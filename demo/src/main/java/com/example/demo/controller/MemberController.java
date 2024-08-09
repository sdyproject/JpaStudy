package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import com.example.demo.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MemberController {

	private final MemberService memberService;

	private final JwtUtil jwtUtil;

	@GetMapping("/")
	public ResponseEntity<?> find() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/member")
	public ResponseEntity<String> join(@RequestBody Member member) {
		memberService.join(member);
		return ResponseEntity.ok().body("회원가입 성공");

	}

	@GetMapping("/current-member")
	public ResponseEntity<?> currentmember(HttpServletRequest request) {
		String accessToken =request.getHeader("access");
		String access =jwtUtil.getUsername(accessToken);
	return ResponseEntity.ok().body(access);
		
		
	}

	@GetMapping("/members")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(memberService.getMembers(), HttpStatus.OK);
	}


	@GetMapping("/members/{id}")
	public ResponseEntity<?> findById(HttpServletRequest request) {
		String accessToken =request.getHeader("access");
		System.out.println(accessToken);
		Long id =jwtUtil.getId(accessToken);
		System.out.println("id :"+id);
		
		System.out.println(memberService.getMember(id));
		
		
		
		return new ResponseEntity<>(memberService.getMember(id), HttpStatus.OK);
	}
	


	@PutMapping("/member/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Member member) {
		return new ResponseEntity<>(memberService.updateMember(id, member), HttpStatus.OK);
	}

	@DeleteMapping("/member/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id, @RequestBody Member member) {
		return new ResponseEntity<>(memberService.deleteMember(id), HttpStatus.OK);
	}

//	// 중복 검사
//	@GetMapping("/member/joinproc/{id}")
//	public ResponseEntity<Boolean> existsById(@PathVariable("id") String id) {
//		return ResponseEntity.ok(memberService.existsById(id));
//
//	}

	@GetMapping("/admin")
	public String admin() {

		return "admin Controller";
	}

}
