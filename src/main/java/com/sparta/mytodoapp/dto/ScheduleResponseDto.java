package com.sparta.mytodoapp.dto;

import com.sparta.mytodoapp.entity.CommentEntity;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String text;
    private String username;
    private boolean complete;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public boolean getComplete() {
        return complete;
    }
}
