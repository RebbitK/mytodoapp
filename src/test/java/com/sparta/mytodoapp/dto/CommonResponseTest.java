package com.sparta.mytodoapp.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommonResponseTest {

    @Test
    @DisplayName("상태 코드 반환 entity 생성 테스트")
    void createCommonResponse() {
        // given
        Integer statusCode = 200;
        String message = "성공하였습니다.";
        String testData = "임시 코드";
        // when
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .statusCode(statusCode)
                .msg(message)
                .data(testData)
                .build();
        // then
        assertNotNull(commonResponse);
        assertEquals(statusCode, commonResponse.getStatusCode());
        assertEquals(message, commonResponse.getMsg());
        assertEquals(testData, commonResponse.getData());
    }

}