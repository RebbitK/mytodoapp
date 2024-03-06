package com.sparta.mytodoapp.exception;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RestApiException> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {
		return createResponse(HttpStatus.BAD_REQUEST,
			e.getBindingResult().getFieldError().getDefaultMessage());
	}

	@ExceptionHandler({
		NoSuchElementException.class,
		BadCredentialsException.class,
		EntityNotFoundException.class,
		IllegalArgumentException.class
	})
	public ResponseEntity<RestApiException> handleBadRequestException(Exception e) {
		return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<RestApiException> handleDuplicateKeyException(DuplicateKeyException e) {
		return createResponse(HttpStatus.CONFLICT, e.getMessage());
	}

	@ExceptionHandler({
		JwtException.class,
		AccessDeniedException.class
	})
	public ResponseEntity<RestApiException> handleJwtException(Exception e) {
		return createResponse(HttpStatus.FORBIDDEN, e.getMessage());
	}

	private ResponseEntity<RestApiException> createResponse(HttpStatus status, String message) {
		return ResponseEntity.status(status.value())
			.body(RestApiException.builder()
				.statusCode(status.value())
				.errorMessage(message)
				.build());
	}
}
