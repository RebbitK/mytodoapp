package com.sparta.mytodoapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.dto.CommentResponseDto;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.entity.Comment;
import com.sparta.mytodoapp.entity.Schedule;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.exception.NoPermissionException;
import com.sparta.mytodoapp.repository.CommentRepository;
import com.sparta.mytodoapp.repository.JpaScheduleRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CommentServiceImplTest {
    @Mock
    CommentRepository commentRepository;
    @Mock
    JpaScheduleRepository jpaScheduleRepository;
    @InjectMocks
    CommentServiceImpl commentService;

    private User testUser() {
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        return new User(username, password, role);
    }

    private Comment testComment() {
        return new Comment("댓글테스트", testUser());
    }

    private Schedule testSchedule() {
        return new Schedule(new ScheduleRequestDto("제목", "내용"), testUser());
    }

    @Test
    @DisplayName("댓글 작성 테스트")
    void createComment() {
        //given
        User user = testUser();
        Schedule schedule = testSchedule();
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        when(jpaScheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        //when
        CommentResponseDto response = commentService.createComment(schedule.getId(),user,commentRequestDto);
        //then
        assertEquals(response.getComment(),commentRequestDto.getComment());
    }
    @Test
    @DisplayName("댓글 작성 실패 테스트")
    void createCommentFail(){
        //given
        User user = testUser();
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        //when
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> commentService.createComment(100L,user,commentRequestDto));
        //then
        assertEquals(exception.getMessage(),"선택하신 할일카드는 존재하지 않습니다.");
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateComment() {
        //given
        User user = testUser();
        Schedule schedule = testSchedule();
        CommentRequestDto commentRequestDto = new CommentRequestDto("수정댓글");
        Comment comment = testComment();
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        //when
        CommentResponseDto response = commentService.updateComment(schedule.getId(),user,commentRequestDto);
        //then
        assertEquals(response.getComment(),commentRequestDto.getComment());
    }

    @Test
    @DisplayName("댓글 수정 실패 - badRequest 테스트")
    void updateCommentFail_badRequest() {
        //given
        User user = testUser();
        Schedule schedule = testSchedule();
        CommentRequestDto commentRequestDto = new CommentRequestDto("수정댓글");
        //when
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> commentService.updateComment(schedule.getId(),user,commentRequestDto));
        //then
        assertEquals(exception.getMessage(),"선택하신 댓글은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("댓글 수정 실패 - forBidden 테스트")
    void updateCommentFail_forBidden() {
        //given
        User user = new User();
        Schedule schedule = testSchedule();
        CommentRequestDto commentRequestDto = new CommentRequestDto("수정댓글");
        Comment comment = testComment();
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        //when
        Exception exception = assertThrows(NoPermissionException.class,
            () -> commentService.updateComment(schedule.getId(),user,commentRequestDto));
        //then
        assertEquals(exception.getMessage(),"선택하신 댓글을 수정할 권한이 없습니다.");
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteComment() {
        //given
        User user = testUser();
        Comment comment = testComment();
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        //when
        Boolean response = commentService.deleteComment(comment.getId(),user);
        //then
        assertTrue(response);
    }

    @Test
    @DisplayName("댓글 삭제 실패 - badRequest 테스트")
    void deleteCommentFail_badRequest(){
        //given
        User user = testUser();
        Comment comment = testComment();
        //when
        Exception exception = assertThrows(IllegalArgumentException.class,
            () ->commentService.deleteComment(comment.getId(),user));
        //then
        assertEquals(exception.getMessage(),"선택하신 댓글은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("댓글 삭제 실패 - forBidden 테스트")
    void deleteCommentFail_forBidden(){
        //given
        User user = new User();
        Comment comment = testComment();
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        //when
        Exception exception = assertThrows(NoPermissionException.class,
            ()->commentService.deleteComment(comment.getId(),user));
        //then
        assertEquals(exception.getMessage(),"선택하신 댓글을 삭제할 권한이 없습니다.");
    }
}