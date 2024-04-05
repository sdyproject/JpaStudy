package com.example.demo.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class JoinDto {

	
	private String username;
	
	private String name;
	
	private String password;
	
	private String hp;
	
	private Date birth;
}
