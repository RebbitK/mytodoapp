package com.sparta.mytodoapp.dto;

import com.sparta.mytodoapp.config.AuditingConfig;
import com.sparta.mytodoapp.entity.Comment;
import com.sparta.mytodoapp.entity.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(AuditingConfig.class)
class ScheduleResponseDtoTest {
    @Test
    @DisplayName("할일카드 responseDto 객체 생성 테스트")
    void createScheduleResponseDto() {
        // given
        Long id = 10L;
        String title = "제몬";
        String text = "내용";
        String username = "Test";
        List<Comment> commentList = new ArrayList<>();

        Schedule schedule = new Schedule();
        schedule.setId(id);
        schedule.setTitle(title);
        schedule.setText(text);
        schedule.setUsername(username);
        schedule.setComments(commentList);

        // when
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        // then
        assertNotNull(scheduleResponseDto);
        assertEquals(id, scheduleResponseDto.getId());
        assertEquals(title, scheduleResponseDto.getTitle());
        assertEquals(text, scheduleResponseDto.getText());
        assertEquals(username, scheduleResponseDto.getUsername());
        assertEquals(false, scheduleResponseDto.getComplete());
        assertEquals(commentList, scheduleResponseDto.getCommentList());
        assertEquals(schedule.getCreatedAt(),scheduleResponseDto.getCreatedAt());
        assertEquals(schedule.getModifiedAt(),scheduleResponseDto.getModifiedAt());
    }
}