package com.sparta.mytodoapp.model;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.dto.CommentResponseDto;
import com.sparta.mytodoapp.entity.CommentEntity;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import com.sparta.mytodoapp.entity.UserEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Comment {

	private Long id;
	private String comment;
	private String username;
	private UserEntity userEntity;
	private ScheduleEntity scheduleEntity;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public static Comment from(CommentEntity commentEntity) {
		return new Comment(
			commentEntity.getId(),
			commentEntity.getComment(),
			commentEntity.getUsername(),
			commentEntity.getUserEntity(),
			commentEntity.getScheduleEntity(),
			commentEntity.getCreatedAt(),
			commentEntity.getModifiedAt()
		);
	}

	public CommentEntity toEntity() {
		return new CommentEntity(
			id,
			comment,
			username,
			userEntity,
			scheduleEntity,
			createdAt,
			modifiedAt
		);
	}

	public CommentResponseDto responseDto() {
		return new CommentResponseDto(
			id,
			comment,
			username,
			createdAt,
			modifiedAt
		);
	}

	public void update(CommentRequestDto requestDto) {
		this.comment = requestDto.getComment();
	}

}
