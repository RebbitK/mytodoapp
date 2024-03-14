package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.dto.CommentResponseDto;
import com.sparta.mytodoapp.entity.CommentEntity;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import com.sparta.mytodoapp.entity.UserEntity;
import com.sparta.mytodoapp.exception.NoPermissionException;
import com.sparta.mytodoapp.model.Comment;
import com.sparta.mytodoapp.repository.CommentRepository;
import com.sparta.mytodoapp.repository.JpaScheduleRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

	private final JpaScheduleRepository jpaScheduleRepository;

	@Override
	@Transactional
	public CommentResponseDto createComment(Long id, UserEntity userEntity, CommentRequestDto requestDto) {
		ScheduleEntity scheduleEntity = jpaScheduleRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 할일카드는 존재하지 않습니다."));
		CommentEntity commentEntity = new CommentEntity(requestDto, userEntity,scheduleEntity);
		commentRepository.save(commentEntity);
		return Comment.from(commentEntity).responseDto();
	}

	@Override
	@Transactional
	public CommentResponseDto updateComment(Long id, UserEntity userEntity, CommentRequestDto requestDto) {
		CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 댓글은 존재하지 않습니다."));
		if (!Objects.equals(commentEntity.getUsername(), userEntity.getUsername())) {
			throw new NoPermissionException("선택하신 댓글을 수정할 권한이 없습니다.");
		}
		Comment.from(commentEntity).update(requestDto);
		return Comment.from(commentEntity).responseDto();
	}

	@Override
	@Transactional
	public Boolean deleteComment(Long id, UserEntity userEntity) {
		CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 댓글은 존재하지 않습니다."));
		if (!Objects.equals(commentEntity.getUsername(), userEntity.getUsername())) {
			throw new NoPermissionException("선택하신 댓글을 삭제할 권한이 없습니다.");
		}
		commentRepository.delete(commentEntity);
		return true;
	}


}
