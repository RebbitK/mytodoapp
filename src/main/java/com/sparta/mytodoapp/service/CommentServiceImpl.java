package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.dto.CommentResponseDto;
import com.sparta.mytodoapp.entity.Comment;
import com.sparta.mytodoapp.entity.Schedule;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.exception.NoPermissionException;
import com.sparta.mytodoapp.repository.CommentRepository;
import com.sparta.mytodoapp.repository.JpaScheduleRepository;
import com.sparta.mytodoapp.repository.ScheduleRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

	private final JpaScheduleRepository jpaScheduleRepository;

	@Override
	@Transactional
	public CommentResponseDto createComment(Long id, User user, CommentRequestDto requestDto) {
		Schedule schedule = jpaScheduleRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 할일카드는 존재하지 않습니다."));
		Comment comment = new Comment(requestDto, user);
		schedule.getComments().add(comment);
		commentRepository.save(comment);
		jpaScheduleRepository.save(schedule);
		return new CommentResponseDto(comment);
	}

	@Override
	@Transactional
	public CommentResponseDto updateComment(Long id, User user, CommentRequestDto requestDto) {
		Comment comment = commentRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 댓글은 존재하지 않습니다."));
		if (!Objects.equals(comment.getUsername(), user.getUsername())) {
			throw new NoPermissionException("선택하신 댓글을 수정할 권한이 없습니다.");
		}
		comment.update(requestDto);
		return new CommentResponseDto(comment);
	}

	@Override
	@Transactional
	public Boolean deleteComment(Long id, User user) {
		Comment comment = commentRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 댓글은 존재하지 않습니다."));
		if (!Objects.equals(comment.getUsername(), user.getUsername())) {
			throw new NoPermissionException("선택하신 댓글을 삭제할 권한이 없습니다.");
		}
		commentRepository.delete(comment);
		return true;
	}


}
