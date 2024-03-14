package com.sparta.mytodoapp.entity;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentEntityTest {
    @Test
    @DisplayName("댓글 작성 테스트")
    void addCommentTest() {
        // Given
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        UserEntity userEntity = new UserEntity("Test", "12345678", UserRoleEnum.USER);
		ScheduleEntity scheduleEntity = new ScheduleEntity();
        // When
        CommentEntity newCommentEntity = new CommentEntity(commentRequestDto, userEntity,scheduleEntity);
        // Then
        assertEquals("댓글", newCommentEntity.getComment());
        assertEquals("Test", newCommentEntity.getUsername());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateCommentTest() {
        // Given
        CommentEntity commentEntity = new CommentEntity();
		commentEntity.setComment("댓글");
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글수정");
        // When
		Comment.from(commentEntity).update(commentRequestDto);
        // Then
        assertEquals("댓글수정", commentEntity.getComment());
    }

}