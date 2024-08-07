package com.example.demo.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import com.example.demo.dto.CustomUserDetails;
import com.example.demo.dto.LoginDto;
import com.example.demo.entity.Member;
import com.example.demo.service.RefreshService;
import com.example.demo.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	private final JwtUtil jwtUtil;

	private final RefreshService refreshService;
	
	

	public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil
			,RefreshService refreshService) {

		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.refreshService = refreshService;
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		// (폼데이터전송)클라이언트 요청에서 username, password 추출
		/*
		 * String username = obtainUsername(request); String password =
		 * obtainPassword(request);
		 */
		LoginDto loginDto = new LoginDto();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ServletInputStream inputStream = request.getInputStream();
			String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
			loginDto = objectMapper.readValue(messageBody, LoginDto.class);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println(loginDto.getUsername());

		String username = loginDto.getUsername();
		String password = loginDto.getPassword();
		// 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
				null);

		// token에 담은 검증을 위한 AuthenticationManager로 전달
		return authenticationManager.authenticate(authToken);
	}

//로그인 성공시 JWT 발급
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) {
		 CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
	      Long id = customUserDetails.getId();
	    
		String username = authentication.getName();
		
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();
		
		
		
		
		// 토큰 생성
		// username , role 동일값들어가고 생명주기 다르게 준다.
		String access = jwtUtil.createJwt("access",id, username, role, 1200000L);
		String refresh = jwtUtil.createJwt("refresh",id, username, role, 86400000L);
		System.out.println("access 토큰 생성 완료 : "+access);
		System.out.println("refresh 토큰 생성 완료 : "+refresh);

		

		//Redis 토큰 생성
		refreshService.setValues(id.toString(), refresh,86400000,TimeUnit.MILLISECONDS);
		
		response.setHeader("access", access);
		response.addCookie(createCookie("refresh", refresh));
		response.setStatus(HttpStatus.OK.value());
	}

//로그인 실패시 실행하는 메소드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) {
		response.setStatus(401);
	}
	

	
//쿠키 생성
	private Cookie createCookie(String key, String value) {

		Cookie cookie = new Cookie(key, value);
		// 쿠키 생명주기
		cookie.setMaxAge(24 * 60 * 60);
		// https 통신 시 필요한 setSecure
		// cookie.setSecure(true);
		// 쿠키 적용 범위 : setPath
		 cookie.setPath("/");
		// 자바스크립트를 통한 쿠기 접근 제어
		cookie.setHttpOnly(true);

		return cookie;
	}
}
