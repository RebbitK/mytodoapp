package com.sparta.mytodoapp.dto;

import com.sparta.mytodoapp.config.JpaConfig;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import com.sparta.mytodoapp.model.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import(JpaConfig.class)
class ScheduleEntityResponseDtoTest {
    @Test
    @DisplayName("할일카드 responseDto 객체 생성 테스트")
    void createScheduleResponseDto() {
        // given
        Long id = 10L;
        String title = "제몬";
        String text = "내용";
        String username = "Test";

        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(id);
        scheduleEntity.setTitle(title);
        scheduleEntity.setText(text);

        // when
        ScheduleResponseDto scheduleResponseDto = Schedule.from(scheduleEntity).responseDto();

        // then
        assertNotNull(scheduleResponseDto);
        assertEquals(id, scheduleResponseDto.getId());
        assertEquals(title, scheduleResponseDto.getTitle());
        assertEquals(text, scheduleResponseDto.getText());
        assertEquals(false, scheduleResponseDto.getComplete());
        assertEquals(scheduleEntity.getCreatedAt(),scheduleResponseDto.getCreatedAt());
        assertEquals(scheduleEntity.getModifiedAt(),scheduleResponseDto.getModifiedAt());
    }
}