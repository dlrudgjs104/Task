package com.nhnacademy.exam.hotel.controller;

import java.rmi.ServerException;

import javax.naming.AuthenticationException;

import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> badRequestException() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청 입니다.");
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<String> authenticationException() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 요청입니다.");
	}

	@ExceptionHandler(ChangeSetPersister.NotFoundException.class)
	public ResponseEntity<String> notFoundException() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("자원을 찾을 수 없습니다.");
	}

	@ExceptionHandler(ServerException.class)
	public ResponseEntity<String> serverException() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
	}

}
