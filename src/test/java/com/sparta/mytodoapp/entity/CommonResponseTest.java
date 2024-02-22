package com.sparta.mytodoapp.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonResponseTest {
    @Test
    @DisplayName("상태 반환 만들기 테스트")
    void createCommonTest() {
        // Given
        Integer statusCode = 200;
        String msg = "성공";
        String data = "아무데이터";
        // When
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(statusCode)
                .msg(msg)
                .data(data)
                .build();
        // Then
        assertEquals(statusCode, response.getStatusCode());
        assertEquals(msg, response.getMsg());
        assertEquals(data, response.getData());
    }

}