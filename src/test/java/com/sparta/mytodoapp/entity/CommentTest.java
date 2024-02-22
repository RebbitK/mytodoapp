package com.sparta.mytodoapp.entity;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentTest {
    @Test
    @DisplayName("댓글 작성 테스트")
    void addCommentTest() {
        // Given
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        User user = new User("Test", "12345678", UserRoleEnum.USER);
        // When
        Comment newComment = new Comment(commentRequestDto, user);
        // Then
        assertEquals("댓글", newComment.getComment());
        assertEquals("Test", newComment.getUsername());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateCommentTest() {
        // Given
        Comment comment = new Comment("댓글", new User("Test", "12345678", UserRoleEnum.USER));
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글수정");
        // When
        comment.update(commentRequestDto);
        // Then
        assertEquals("댓글수정", comment.getComment());
    }

}