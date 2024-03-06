package com.sparta.mytodoapp.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RestApiException {
	private String errorMessage;
	private int statusCode;
}