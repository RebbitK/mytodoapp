package com.sparta.mytodoapp.dto;

import com.sparta.mytodoapp.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfoResponseDtoTest {
    @Test
    @DisplayName("유저 정보 객체 생성 테스트")
    void createInfoResponseDto() {
        // given
        String username = "Test";
        UserRoleEnum role = UserRoleEnum.USER;

        // when
        InfoResponseDto infoResponseDto = new InfoResponseDto(username, role);

        // then
        assertNotNull(infoResponseDto);
        assertEquals(username, infoResponseDto.getUsername());
        assertEquals(role, infoResponseDto.getRole());
    }

}