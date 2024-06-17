package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ReissueController {

	
	
	
	private final MemberService memberService;
	
	public ReissueController(MemberService memberService){
		
		this.memberService = memberService;
	}
	
	@PostMapping("/reissue") 
	public ResponseEntity<?> reissue(HttpServletRequest request,  HttpServletResponse response){
		memberService.reissue(request, response);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
