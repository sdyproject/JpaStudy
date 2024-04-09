package com.example.demo.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.dto.CustomUserDetails;
import com.example.demo.utils.JwtUtil;
import com.example.demo.vo.Member;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTFilter extends OncePerRequestFilter{
	
	private final JwtUtil jwtUtil;
	
	public JWTFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	} 
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 String authorization= request.getHeader("Authorization");
		 
		  if (authorization == null || !authorization.startsWith("Bearer ")) {

	            System.out.println("token null");
	            filterChain.doFilter(request, response);
							
							//조건이 해당되면 메소드 종료 (필수)
	            return;
	        }
		  System.out.println("authorization now");
		  String token = authorization.split(" ")[1];
		  
		  if (jwtUtil.isExpired(token)) {

	            System.out.println("token expired");
	            filterChain.doFilter(request, response);

							//조건이 해당되면 메소드 종료 (필수)
	            return;
	        }
		  
		  String username = jwtUtil.getUsername(token);
		  String role = jwtUtil.getRole(token);
		  
		  Member member = new Member();
		  member.setUsername(username);
		  member.setPassword("temppassword");
		  member.setRole(role);
		  
		  CustomUserDetails customUserDetails = new CustomUserDetails(member);
		  
		  Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		  
		  SecurityContextHolder.getContext().setAuthentication(authToken);
		  
		  filterChain.doFilter(request, response);
	}

}
