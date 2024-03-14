package com.sparta.mytodoapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.dto.CommentResponseDto;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.entity.CommentEntity;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import com.sparta.mytodoapp.entity.UserEntity;
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
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CommentEntityServiceImplTest {
    @Mock
    CommentRepository commentRepository;
    @Mock
    JpaScheduleRepository jpaScheduleRepository;
    @InjectMocks
    CommentServiceImpl commentService;

    private UserEntity testUser() {
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        return new UserEntity(username, password, role);
    }

    private CommentEntity testComment() {
        return new CommentEntity("댓글테스트", testUser());
    }

    private ScheduleEntity testSchedule() {
        return new ScheduleEntity(new ScheduleRequestDto("제목", "내용"), testUser());
    }

    @Test
    @DisplayName("댓글 작성 테스트")
    void createComment() {
        //given
        UserEntity userEntity = testUser();
        ScheduleEntity scheduleEntity = testSchedule();
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        when(jpaScheduleRepository.findById(scheduleEntity.getId())).thenReturn(Optional.of(
            scheduleEntity));
        //when
        CommentResponseDto response = commentService.createComment(scheduleEntity.getId(),
            userEntity,commentRequestDto);
        //then
        assertEquals(response.getComment(),commentRequestDto.getComment());
    }
    @Test
    @DisplayName("댓글 작성 실패 테스트")
    void createCommentFail(){
        //given
        UserEntity userEntity = testUser();
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        //when
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> commentService.createComment(100L, userEntity,commentRequestDto));
        //then
        assertEquals(exception.getMessage(),"선택하신 할일카드는 존재하지 않습니다.");
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateComment() {
        //given
        UserEntity userEntity = testUser();
        ScheduleEntity scheduleEntity = testSchedule();
        CommentRequestDto commentRequestDto = new CommentRequestDto("수정댓글");
        CommentEntity commentEntity = testComment();
        when(commentRepository.findById(commentEntity.getId())).thenReturn(Optional.of(
			commentEntity));
        //when
        CommentResponseDto response = commentService.updateComment(scheduleEntity.getId(),
            userEntity,commentRequestDto);
        //then
        assertEquals(response.getComment(),commentRequestDto.getComment());
    }

    @Test
    @DisplayName("댓글 수정 실패 - badRequest 테스트")
    void updateCommentFail_badRequest() {
        //given
        UserEntity userEntity = testUser();
        ScheduleEntity scheduleEntity = testSchedule();
        CommentRequestDto commentRequestDto = new CommentRequestDto("수정댓글");
        //when
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> commentService.updateComment(scheduleEntity.getId(), userEntity,commentRequestDto));
        //then
        assertEquals(exception.getMessage(),"선택하신 댓글은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("댓글 수정 실패 - forBidden 테스트")
    void updateCommentFail_forBidden() {
        //given
        UserEntity userEntity = new UserEntity();
        ScheduleEntity scheduleEntity = testSchedule();
        CommentRequestDto commentRequestDto = new CommentRequestDto("수정댓글");
        CommentEntity commentEntity = testComment();
        when(commentRepository.findById(commentEntity.getId())).thenReturn(Optional.of(
			commentEntity));
        //when
        Exception exception = assertThrows(NoPermissionException.class,
            () -> commentService.updateComment(scheduleEntity.getId(), userEntity,commentRequestDto));
        //then
        assertEquals(exception.getMessage(),"선택하신 댓글을 수정할 권한이 없습니다.");
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteComment() {
        //given
        UserEntity userEntity = testUser();
        CommentEntity commentEntity = testComment();
        when(commentRepository.findById(commentEntity.getId())).thenReturn(Optional.of(
			commentEntity));
        //when
        Boolean response = commentService.deleteComment(commentEntity.getId(), userEntity);
        //then
        assertTrue(response);
    }

    @Test
    @DisplayName("댓글 삭제 실패 - badRequest 테스트")
    void deleteCommentFail_badRequest(){
        //given
        UserEntity userEntity = testUser();
        CommentEntity commentEntity = testComment();
        //when
        Exception exception = assertThrows(IllegalArgumentException.class,
            () ->commentService.deleteComment(commentEntity.getId(), userEntity));
        //then
        assertEquals(exception.getMessage(),"선택하신 댓글은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("댓글 삭제 실패 - forBidden 테스트")
    void deleteCommentFail_forBidden(){
        //given
        UserEntity userEntity = new UserEntity();
        CommentEntity commentEntity = testComment();
        when(commentRepository.findById(commentEntity.getId())).thenReturn(Optional.of(
			commentEntity));
        //when
        Exception exception = assertThrows(NoPermissionException.class,
            ()->commentService.deleteComment(commentEntity.getId(), userEntity));
        //then
        assertEquals(exception.getMessage(),"선택하신 댓글을 삭제할 권한이 없습니다.");
    }
}