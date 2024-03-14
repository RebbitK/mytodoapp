package com.sparta.mytodoapp.model;

import com.sparta.mytodoapp.dto.CommentResponseDto;
import com.sparta.mytodoapp.dto.GetScheduleDto;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.dto.ScheduleResponseDto;
import com.sparta.mytodoapp.entity.CommentEntity;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import com.sparta.mytodoapp.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
	private Long id;
	private String title;
	private String text;
	private String username;
	private UserEntity userEntity;
	private boolean complete;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public static Schedule from(ScheduleEntity scheduleEntity){
		return new Schedule(
			scheduleEntity.getId(),
			scheduleEntity.getTitle(),
			scheduleEntity.getText(),
			scheduleEntity.getUsername(),
			scheduleEntity.getUserEntity(),
			scheduleEntity.getComplete(),
			scheduleEntity.getCreatedAt(),
			scheduleEntity.getModifiedAt()
		);
	}

	public ScheduleEntity toEntity(){
		return new ScheduleEntity(
			id,
			title,
			text,
			username,
			userEntity,
			complete,
			createdAt,
			modifiedAt
		);
	}
	public ScheduleResponseDto responseDto(){
		return new ScheduleResponseDto(
			id,
			title,
			text,
			username,
			complete,
			createdAt,
			modifiedAt
		);
	}

	public GetScheduleDto getResponseDto(List<CommentResponseDto> commentResponseDtos){
		return new GetScheduleDto(
			id,
			title,
			text,
			username,
			complete,
			createdAt,
			modifiedAt,
			commentResponseDtos
		);
	}

	public void update(ScheduleRequestDto requestDto) {
		this.title = requestDto.getTitle();
		this.text= requestDto.getText();
	}
}
