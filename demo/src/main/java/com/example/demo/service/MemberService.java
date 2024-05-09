package com.example.demo.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Member;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.MemberRepository;
import com.example.demo.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	
	@Transactional
	public String join(Member member) {

		String username = member.getUsername();
		String password = member.getPassword();
		Date birth = member.getBirth();
		String hp = member.getHp();
		String name = member.getName();
		
		
		boolean ex = memberRepository.existsByUsername(username);
		System.out.println(ex);
		if (ex) {
			throw new
			  AppException(ErrorCode.USER_DUPLICATED, username + " 이미 존재하는 아이디 입니다.");
		}
		
		
		 

		Member data = new Member();
		data.setUsername(username);
		data.setPassword(bCryptPasswordEncoder.encode(password));
		data.setBirth(birth);
		data.setName(name);
		data.setHp(hp);
		data.setRole("ROLE_ADMIN");
		memberRepository.save(data);
		return "SUCCESS";

	}

	

	@Transactional(readOnly = true)
	public Member 한건가져오기(Long num) {
		return memberRepository.findById(num).orElseThrow(() -> new IllegalArgumentException("num 확인해주세요"));
	}

	@Transactional(readOnly = true)
	public List<Member> 모두가져오기() {
		return memberRepository.findAll();
	}

	@Transactional
	public Member 수정하기(Long num, Member member) {
		Member memberVoEntity = memberRepository.findById(num)
				.orElseThrow(() -> new IllegalArgumentException("num 확인해주세요"));
		memberVoEntity.setPassword(member.getPassword());
		memberVoEntity.setHp(member.getHp());
		return memberVoEntity;
	}

	@Transactional
	public String 삭제하기(Long num) {
		memberRepository.deleteById(num);
		return "ok";
	}

	// 중복 여부확인

	@Transactional
	public boolean existsById(String username) {
		return memberRepository.existsByUsername(username);
	}

}
