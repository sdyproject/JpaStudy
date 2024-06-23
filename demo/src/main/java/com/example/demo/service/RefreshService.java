package com.example.demo.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;

@Service
public class RefreshService {

	private final RedisTemplate<String, Object> redisTemplate;
	
	  public RefreshService(RedisTemplate<String, Object> redisTemplate) {

	       
	        this.redisTemplate = redisTemplate;
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
}
