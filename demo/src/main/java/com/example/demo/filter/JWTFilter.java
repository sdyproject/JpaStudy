package com.example.demo.filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.dto.CustomUserDetails;
import com.example.demo.entity.Member;
import com.example.demo.utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
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
		
		String accessToken = request.getHeader("access");
		
		
		if (accessToken == null) {

		    filterChain.doFilter(request, response);

		    return;
		}

		
		try {
		    jwtUtil.isExpired(accessToken);
		} catch (ExpiredJwtException e) {

		    
		    PrintWriter writer = response.getWriter();
		    writer.print("access token expired");

		    
		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    return;
		}

		
		String category = jwtUtil.getCategory(accessToken);

		if (!category.equals("access")) {

		    
		    PrintWriter writer = response.getWriter();
		    writer.print("invalid access token");

		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    return;
		}
		 
		
		String username = jwtUtil.getUsername(accessToken);
		System.out.println("username :"+username);
		String role = jwtUtil.getRole(accessToken);
		Long mid = jwtUtil.getId(accessToken);
		String id = String.valueOf(mid);
		System.out.println("role :"+role);

		 Member member = new Member();
		member.setUsername(username);
		
		member.setRole(role);
		CustomUserDetails customUserDetails = new CustomUserDetails(member);

		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);
	}
	

}
