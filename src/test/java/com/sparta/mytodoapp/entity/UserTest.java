package com.sparta.mytodoapp.entity;

import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("유저 만들기 테스트")
    void createUserTest() {
        // Given
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        // When
        User user = new User(username, password, role);
        Schedule schedule1 = new Schedule(new ScheduleRequestDto("제목1", "내용1"), user);
        Schedule schedule2 = new Schedule(new ScheduleRequestDto("제목2", "내용2"), user);
        user.getSchedules().add(schedule1);
        user.getSchedules().add(schedule2);
        // Then
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(schedule1);
        schedules.add(schedule2);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
        assertEquals(schedules,user.getSchedules());
    }

}