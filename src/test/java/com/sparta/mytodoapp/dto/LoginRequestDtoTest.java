package com.sparta.mytodoapp.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestDtoTest {
    @Test
    @DisplayName("로그인 requestDto 객체 생성 테스트")
    void createLoginRequestDto() {
        // given
        String username = "Test";
        String password = "12345678";
        // when
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername(username);
        loginRequestDto.setPassword(password);
        // then
        assertNotNull(loginRequestDto);
        assertEquals(username, loginRequestDto.getUsername());
        assertEquals(password, loginRequestDto.getPassword());
    }
}