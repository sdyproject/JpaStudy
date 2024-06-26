package com.example.demo.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	
	USER_DUPLICATED(HttpStatus.CONFLICT,""),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND,""),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,""),
	REFRESH_TOKEN_NULL(HttpStatus.BAD_REQUEST,""),
	REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,""),	
	INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST,""),
	ACCESS_TOKEN_NULL(HttpStatus.BAD_REQUEST,""),
	;
	private HttpStatus httpStatus;
	private String message;
}
