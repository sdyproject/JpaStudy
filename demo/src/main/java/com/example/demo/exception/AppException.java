package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException{
	private ErrorCode errorCode;
	String message;
}