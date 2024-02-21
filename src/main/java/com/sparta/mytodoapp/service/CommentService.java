package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.dto.CommentResponseDto;
import com.sparta.mytodoapp.dto.CommonResponse;
import com.sparta.mytodoapp.entity.Comment;
import com.sparta.mytodoapp.entity.Schedule;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.repository.CommentRepository;
import com.sparta.mytodoapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.sparta.mytodoapp.service.StatusCheck.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ResponseEntity<CommonResponse<?>> createComment(Long id, User user, CommentRequestDto requestDto) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if(schedule.isEmpty()){
            return badRequest("선택하신 할일카드는 존재하지 않습니다.");
        }
        Comment comment = new Comment(requestDto, user);
        schedule.get().getComments().add(comment);
        commentRepository.save(comment);
        scheduleRepository.save(schedule.get());
        return success("댓글 작성에 성공하셨습니다.",new CommentResponseDto(comment));
    }

    @Transactional
    public ResponseEntity<CommonResponse<?>> updateComment(Long id, User user, CommentRequestDto requestDto) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()){
            return badRequest("선택하신 댓글은 존재하지 않습니다.");
        }
        if(!Objects.equals(comment.get().getUsername(), user.getUsername())){
            return forBidden("선택하신 댓글을 수정하실 권한이 없습니다.");
        }
        comment.get().update(requestDto);
        return success("댓글 수정에 성공하셨습니다.",new CommentResponseDto(comment.get()));
    }

    @Transactional
    public ResponseEntity<CommonResponse<?>> deleteComment(Long id, User user) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()){
            return badRequest("선택하신 댓글은 존재하지 않습니다.");
        }
        if(!Objects.equals(comment.get().getUsername(), user.getUsername())){
            return forBidden("선택하신 댓글을 삭제하실 권한이 없습니다.");
        }
        commentRepository.delete(comment.get());
        return success("댓글 삭제에 성공하셨습니다.","");
    }


}
