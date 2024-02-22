package com.sparta.mytodoapp.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("SignupRequestDto 유효성 검사 테스트")
    void validateSignupRequestDto() {
        // given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("testuser");
        signupRequestDto.setPassword("password123");

        // when
        var violations = validator.validate(signupRequestDto);

        // then
        assertTrue(violations.isEmpty());
        assertEquals("testuser", signupRequestDto.getUsername());
        assertEquals("password123", signupRequestDto.getPassword());
    }
}