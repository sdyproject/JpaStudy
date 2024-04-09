package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MemberService;
import com.example.demo.vo.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class DemoController {

	private final MemberService memberService;

	@GetMapping("/")
	public ResponseEntity<?> find() {
		return new ResponseEntity<String>("ok", HttpStatus.NOT_FOUND);
	}

	@PostMapping("/member")
	public ResponseEntity<String> join(@RequestBody Member member) {
		memberService.join(member);
		return ResponseEntity.ok().body("회원가입 성공");

	}

	
	/*
	 * @PostMapping("/login") public ResponseEntity<?> login(@RequestBody Member
	 * member) { String token =
	 * memberService.login(member.getId(),member.getPassword()); return
	 * ResponseEntity.ok().body("성공"); }
	 */
	 

	@GetMapping("/member")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(memberService.모두가져오기(), HttpStatus.OK);
	}

	@GetMapping("/member/{num}")
	public ResponseEntity<?> findById(@PathVariable Long num) {
		return new ResponseEntity<>(memberService.한건가져오기(num), HttpStatus.OK);
	}

	@PutMapping("/member/{num}")
	public ResponseEntity<?> update(@PathVariable Long num, @RequestBody Member member) {
		return new ResponseEntity<>(memberService.수정하기(num, member), HttpStatus.OK);
	}

	@DeleteMapping("/member/{num}")
	public ResponseEntity<?> deleteById(@PathVariable Long num, @RequestBody Member member) {
		return new ResponseEntity<>(memberService.삭제하기(num), HttpStatus.OK);
	}

	// 중복 검사
	@GetMapping("/member/joinproc/{id}")
	public ResponseEntity<Boolean> existsById(@PathVariable("id") String id) {
		return ResponseEntity.ok(memberService.existsById(id));

	}

	@GetMapping("/hello")
	public ResponseEntity<?> hello() {
		return ResponseEntity.ok("hello");
	}

	@GetMapping("/api/demo")
	public List<String> Hello() {
		return Arrays.asList("React+Spring", "연결 성공");
	}
	
	@GetMapping("/admin")
    public String admin() {

        return "admin Controller";
    }
	
}
