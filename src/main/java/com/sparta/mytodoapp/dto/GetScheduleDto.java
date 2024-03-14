package com.sparta.mytodoapp.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetScheduleDto {

	private Long id;
	private String title;
	private String text;
	private String username;
	private boolean complete;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private List<CommentResponseDto> commentResponseDtos;


}
