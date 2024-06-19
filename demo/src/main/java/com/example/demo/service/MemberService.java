package com.example.demo.service;

import java.sql.Date;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Member;
import com.example.demo.entity.RefreshToken;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final JwtUtil jwtUtil;

	private final RefreshTokenRepository refreshTokenRepository;

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
			throw new AppException(ErrorCode.USER_DUPLICATED, username + " 이미 존재하는 아이디 입니다.");
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

	public void reissue(HttpServletRequest request, HttpServletResponse response) {
		String refresh = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("refresh")) {

				refresh = cookie.getValue();
			}

		}

		if (refresh == null) {
			throw new AppException(ErrorCode.REFRESH_TOKEN_NULL, "refresh token null");
		}
		try {
			jwtUtil.isExpired(refresh);
		} catch (ExpiredJwtException e) {
			throw new AppException(ErrorCode.REFRESH_TOKEN_EXPIRED, "refresh token expired");

		}
		String category = jwtUtil.getCategory(refresh);

		if (!category.equals("refresh")) {
			throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN, "invalid refresh token");

		}
		
		Boolean isExist = refreshTokenRepository.existsByRefresh(refresh);
		if (!isExist) {
			throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN, "(existbyfresh)invalid refresh token");

		}

		String username = jwtUtil.getUsername(refresh);
		String role = jwtUtil.getRole(refresh);

		String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
		String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);
		
		refreshTokenRepository.deleteByRefresh(refresh);
		addRefreshEntity(username, newRefresh);

		response.setHeader("access", newAccess);
		response.addCookie(createCookie("refresh", newRefresh));

	}		
	private void addRefreshEntity(String username, String refresh) {

	    RefreshToken refreshToken = new RefreshToken();
	    refreshToken.setUsername(username);
		refreshToken.setRefresh(refresh);
	    refreshTokenRepository.save(refreshToken);
	}

	
	private Cookie createCookie(String key, String value) {

		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(24 * 60 * 60);
		// cookie.setSecure(true);
		// cookie.setPath("/");
		cookie.setHttpOnly(true);

		return cookie;
	}

}
