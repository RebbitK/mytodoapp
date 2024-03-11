package com.sparta.mytodoapp.entity;

import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    @Test
    @DisplayName("할일카드 만들기 테스트")
    void createScheduleTest() {
        // Given
        String title = "제목";
        String text = "내용";
        ScheduleRequestDto requestDto = new ScheduleRequestDto(title, text);
        User user = new User("Test", "12345678", UserRoleEnum.USER);
        // When
        Schedule schedule = new Schedule(requestDto, user);
        // Then
        assertEquals(title, schedule.getTitle());
        assertEquals(text, schedule.getText());
        assertEquals(user.getUsername(), schedule.getUser().getUsername());
        assertFalse(schedule.getComplete());
        assertTrue(schedule.getComments().isEmpty());
    }

    @Test
    @DisplayName("할일카드 수정 테스트")
    void updateScheduleTest() {
        // Given
        String newTitle = "제목수정";
        String newText = "내용수정";
        ScheduleRequestDto requestDto = new ScheduleRequestDto(newTitle, newText);
        Schedule schedule = new Schedule(new ScheduleRequestDto("제목", "내용"), new User("Test", "12345678", UserRoleEnum.USER));
        // When
        schedule.update(requestDto);
        // Then
        assertEquals(newTitle, schedule.getTitle());
        assertEquals(newText, schedule.getText());
    }

}