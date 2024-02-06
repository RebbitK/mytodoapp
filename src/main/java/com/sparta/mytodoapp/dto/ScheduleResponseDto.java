package com.sparta.mytodoapp.dto;

import com.sparta.mytodoapp.entity.Comment;
import com.sparta.mytodoapp.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String text;
    private String username;
    private boolean complete;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<Comment> commentList;


    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.text = schedule.getText();
        this.username = schedule.getUsername();
        this.complete = schedule.getComplete();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.commentList = schedule.getComments();
    }
}
