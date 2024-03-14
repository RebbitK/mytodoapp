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
        ScheduleEntity scheduleEntity1 = new ScheduleEntity(new ScheduleRequestDto("제목1", "내용1"),
            userEntity);
        ScheduleEntity scheduleEntity2 = new ScheduleEntity(new ScheduleRequestDto("제목2", "내용2"),
            userEntity);
        userEntity.getSchedules().add(scheduleEntity1);
        userEntity.getSchedules().add(scheduleEntity2);
        // Then
        List<ScheduleEntity> scheduleEntities = new ArrayList<>();
        scheduleEntities.add(scheduleEntity1);
        scheduleEntities.add(scheduleEntity2);
        assertEquals(username, userEntity.getUsername());
        assertEquals(password, userEntity.getPassword());
        assertEquals(role, userEntity.getRole());
        assertEquals(scheduleEntities, userEntity.getSchedules());
    }

}