package com.example.demo.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class RefreshService {

	private final RedisTemplate<String, Object> redisTemplate;
	
	private final JwtUtil jwtUtil;
	
	  public RefreshService(RedisTemplate<String, Object> redisTemplate,JwtUtil jwtUtil) {

	       
	        this.redisTemplate = redisTemplate;
	        this.jwtUtil = jwtUtil;
	    }
	
	  
	  public void setValues(String key, String value) {
			ValueOperations<String, Object> values = redisTemplate.opsForValue();
			values.set(key, value);
		}

		public void getValues(String key) {
			ValueOperations<String, Object> values = redisTemplate.opsForValue();
			if (values.get(key) == null) {
				throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN, "(existbyfresh)invalid refresh token");
			}
			
		}
		
		public void deleteValue(String key) {
	        redisTemplate.delete(key);
	    }
		
		/*
		 * public String extractTokenFromRequest (HttpServletRequest request) { String
		 * accessToken =request.getHeader("access"); if (accessToken == null) { throw
		 * new AppException(ErrorCode.ACCESS_TOKEN_NULL, "access token null"); } Long
		 * access =jwtUtil.getId(accessToken); return access.toString().substring(7); }
		 */
		
		
}
