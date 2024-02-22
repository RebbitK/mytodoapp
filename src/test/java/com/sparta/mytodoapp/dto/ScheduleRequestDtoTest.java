package com.sparta.mytodoapp.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleRequestDtoTest {

    @Test
    @DisplayName("할일 카드 requestDto 객체 생성 테스트")
    void createScheduleRequestDto() {
        // given
        String title = "제목";
        String text = "내용";
        // when
        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto(title, text);
        // then
        assertNotNull(scheduleRequestDto);
        assertEquals(title, scheduleRequestDto.getTitle());
        assertEquals(text, scheduleRequestDto.getText());
    }
}