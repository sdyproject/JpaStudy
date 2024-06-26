package com.example.demo.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long boardid;
	
	private String boardname;
	
	private String boardcontext;
	
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime  boardwrite;
	
	private Date boardschedule;
	
	private Long id;
}
