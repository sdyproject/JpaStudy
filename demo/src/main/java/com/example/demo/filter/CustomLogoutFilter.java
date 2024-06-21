package com.example.demo.filter;

import java.io.IOException;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.filter.GenericFilterBean;

import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLogoutFilter extends GenericFilterBean{
	
	private final JwtUtil jwtUtil;
    
    private final RedisTemplate<String, Object> redisTemplate;

    public CustomLogoutFilter(JwtUtil jwtUtil,RedisTemplate<String, Object> redisTemplate) {

        this.jwtUtil = jwtUtil;
       
        this.redisTemplate = redisTemplate;
    }
    
   
    
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);

		
	}
	private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		  String requestUri = request.getRequestURI();
	        if (!requestUri.matches("^\\/logout$")) {

	            filterChain.doFilter(request, response);
	            return;
	        }
	        String requestMethod = request.getMethod();
	        if (!requestMethod.equals("POST")) {

	            filterChain.doFilter(request, response);
	            return;
	        }

	        //get refresh token
	        String refresh = null;
	        Cookie[] cookies = request.getCookies();
	        for (Cookie cookie : cookies) {

	            if (cookie.getName().equals("refresh")) {

	                refresh = cookie.getValue();
	            }
	        }

	        //refresh null check
	        if (refresh == null) {

	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            return;
	        }

	        //expired check
	        try {
	            jwtUtil.isExpired(refresh);
	        } catch (ExpiredJwtException e) {

	            //response status code
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            return;
	        }

	        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
	        String category = jwtUtil.getCategory(refresh);
	        if (!category.equals("refresh")) {

	            //response status code
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            return;
	        }
	        
	        
	        String username = jwtUtil.getUsername(refresh);
	        System.out.println(username);
			//redis Token 조회
	    	getValues(username);
	    	System.out.println(username);
	    	//refreshToken 삭제
	    	deleteValue(username);
	    	System.out.println(refresh);
	       
	        

	        //Refresh 토큰 Cookie 값 0
	        Cookie cookie = new Cookie("refresh", null);
	        cookie.setMaxAge(0);
	        cookie.setPath("/api");

	        response.addCookie(cookie);
	        response.setStatus(HttpServletResponse.SC_OK);
	}
			private void getValues(String key) {
			ValueOperations<String, Object> values = redisTemplate.opsForValue();
			if (values.get(key) == null) {
				throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN, "(existbyfresh)invalid refresh token");
				}
			
			}
			
			private void deleteValue(String key) {
		        redisTemplate.delete(key);
		    }
		

}

