package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.dto.CommentResponseDto;
import com.sparta.mytodoapp.dto.StateResponseDto;
import com.sparta.mytodoapp.entity.Comment;
import com.sparta.mytodoapp.entity.Schedule;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.repository.CommentRepository;
import com.sparta.mytodoapp.repository.ScheduleRepository;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentResponseDto createComment(Long id, UserDetailsImpl userDetails, CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto, userDetails.getUser());
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택하신 일정이 없습니다.")
        );
        schedule.getComments().add(comment);
        commentRepository.save(comment);
        scheduleRepository.save(schedule);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public ResponseEntity<?> updateComment(Long id, UserDetailsImpl userDetails, CommentRequestDto requestDto) {
        Object object = findComment(id, userDetails).getBody();
        if(object instanceof StateResponseDto){
            return ResponseEntity.status(400).body(object);
        }
        Comment comment = (Comment) object;
        assert comment != null;
        comment.update(requestDto);
        return ResponseEntity.ok(new CommentResponseDto(comment));
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long id, UserDetailsImpl userDetails) {
        Object object = findComment(id, userDetails).getBody();
        if(object instanceof StateResponseDto){
            return ResponseEntity.status(400).body(object);
        }
        Comment comment = (Comment) object;
        assert comment != null;
        commentRepository.delete(comment);
        return ResponseEntity.ok("삭제하였습니다.");
    }

    private ResponseEntity<?> findComment(Long id, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당하는 댓글은 없습니다.")
        );
        User user = userDetails.getUser();
        if (!comment.getUsername().equals(user.getUsername())) {
            return ResponseEntity.status(400).body(new StateResponseDto("400","권한이 없습니다."));
        }
        return ResponseEntity.ok(comment);
    }

}
