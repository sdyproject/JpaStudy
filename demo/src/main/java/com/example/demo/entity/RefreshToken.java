package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RedisHash(value = "refresh", timeToLive = 86400000)
public class RefreshToken {

	@Id
	private Long id;
	
	private String refresh;
	private String username; 
	
	
	

}
