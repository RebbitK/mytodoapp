package com.sparta.mytodoapp.dto;

import com.sparta.mytodoapp.config.AuditingConfig;
import com.sparta.mytodoapp.entity.CommentEntity;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(AuditingConfig.class)
class ScheduleEntityResponseDtoTest {
    @Test
    @DisplayName("할일카드 responseDto 객체 생성 테스트")
    void createScheduleResponseDto() {
        // given
        Long id = 10L;
        String title = "제몬";
        String text = "내용";
        String username = "Test";
        List<CommentEntity> commentEntityList = new ArrayList<>();

        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(id);
        scheduleEntity.setTitle(title);
        scheduleEntity.setText(text);
        scheduleEntity.setComments(commentEntityList);

        // when
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(scheduleEntity);

        // then
        assertNotNull(scheduleResponseDto);
        assertEquals(id, scheduleResponseDto.getId());
        assertEquals(title, scheduleResponseDto.getTitle());
        assertEquals(text, scheduleResponseDto.getText());
        assertEquals(username, scheduleResponseDto.getUsername());
        assertEquals(false, scheduleResponseDto.getComplete());
        assertEquals(commentEntityList, scheduleResponseDto.getCommentEntityList());
        assertEquals(scheduleEntity.getCreatedAt(),scheduleResponseDto.getCreatedAt());
        assertEquals(scheduleEntity.getModifiedAt(),scheduleResponseDto.getModifiedAt());
    }
}