package com.sparta.mytodoapp.entity;

import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.model.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleEntityTest {
    @Test
    @DisplayName("할일카드 만들기 테스트")
    void createScheduleTest() {
        // Given
        String title = "제목";
        String text = "내용";
        ScheduleRequestDto requestDto = new ScheduleRequestDto(title, text);
        UserEntity userEntity = new UserEntity("Test", "12345678", UserRoleEnum.USER);
        // When
        ScheduleEntity scheduleEntity = new ScheduleEntity(requestDto, userEntity);
        // Then
        assertEquals(title, scheduleEntity.getTitle());
        assertEquals(text, scheduleEntity.getText());
        assertEquals(userEntity.getUsername(), scheduleEntity.getUsername());
        assertFalse(scheduleEntity.getComplete());
    }

    @Test
    @DisplayName("할일카드 수정 테스트")
    void updateScheduleTest() {
        // Given
        String newTitle = "제목수정";
        String newText = "내용수정";
        ScheduleRequestDto requestDto = new ScheduleRequestDto(newTitle, newText);
        ScheduleEntity scheduleEntity = new ScheduleEntity(new ScheduleRequestDto("제목", "내용"), new UserEntity("Test", "12345678", UserRoleEnum.USER));
        Schedule schedule = Schedule.from(scheduleEntity);
        // When
        schedule.update(requestDto);
        // Then
        assertEquals(newTitle, schedule.toEntity().getTitle());
        assertEquals(newText, schedule.toEntity().getText());
    }

}