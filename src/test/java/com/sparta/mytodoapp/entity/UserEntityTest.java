package com.sparta.mytodoapp.entity;

import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    @DisplayName("유저 만들기 테스트")
    void createUserTest() {
        // Given
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        // When
        UserEntity userEntity = new UserEntity(username, password, role);
        // Then
        assertEquals(username, userEntity.getUsername());
        assertEquals(password, userEntity.getPassword());
        assertEquals(role, userEntity.getRole());
    }

}