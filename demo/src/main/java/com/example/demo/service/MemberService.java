package com.example.demo.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.MemberRepository;
import com.example.demo.utils.JwtUtil;
import com.example.demo.vo.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/*
	 * @Value("${jwt.token.secret}") private String key; private Long expireTimeMs =
	 * 1000 * 60 * 60l;
	 */

	@Transactional
	public String join(Member member) {

		String username = member.getUsername();
		String password = member.getPassword();
		Date birth = member.getBirth();
		String hp = member.getHp();
		String name = member.getName();
		
		
		boolean ex = memberRepository.existsByUsername(username);
		if (ex) {
			throw new
			  AppException(ErrorCode.ID_DUPLICATED, username + " 이미 존재하는 아이디 입니다.");
		}
		
		/*
		 * memberRepository.findByUsername(user_name) { throw new
		 * AppException(ErrorCode.ID_DUPLICATED, user_name + " 이미 존재하는 아이디 입니다."); });
		 */
		 

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

	/*
	 * @Transactional public String login(String username,String password) { //id없음
	 * Member selectedMember = memberRepository.findByUsername(username)
	 * .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND, username
	 * +"이 없습니다.")); //password없음 if(!bCryptPasswordEncoder.matches(password,
	 * selectedMember.getPassword())) { throw new
	 * AppException(ErrorCode.INVALID_PASSWORD, "비밀번호 잘못 입력 했습니다."); }
	 * 
	 * 
	 * String token =
	 * JwtTokenUtil.createToken(selectedMember.getId(),key,expireTimeMs);
	 * 
	 * 
	 * return "SUCCESS"; }
	 */

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
