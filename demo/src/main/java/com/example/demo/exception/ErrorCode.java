package com.example.demo.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	
	ID_DUPLICATED(HttpStatus.CONFLICT,""),
	ID_NOT_FOUND(HttpStatus.NOT_FOUND,""),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"")
	;
	private HttpStatus httpStatus;
	private String message;
}
