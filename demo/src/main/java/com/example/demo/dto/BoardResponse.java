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
public class BoardResponse {
	private Long boardid;
	
	private String boardname;
	
	private String boardcontext;
	
	private Date boardwrite;
	
	private Long id;
}
