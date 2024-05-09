package com.example.demo.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

	private Long id;
	
	private String username;
	
	private String name;
	
	private String password;
	
	private String hp;
	
	private Date birth;
	
	private String role;
}
