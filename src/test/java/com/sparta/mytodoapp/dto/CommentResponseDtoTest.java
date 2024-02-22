package com.sparta.mytodoapp.dto;

import com.sparta.mytodoapp.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentResponseDtoTest {

    @Test
    @DisplayName("댓글 responseDto 생성 테스트")
    void createCommentResponseDto() {
        // given
        Long id = 1L;
        String commentText = "댓글";
        String username = "test_user";
        Comment comment = new Comment();
        comment.setId(id);
        comment.setComment(commentText);
        comment.setUsername(username);

        // when
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        // then
        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(commentText, responseDto.getComment());
        assertEquals(username, responseDto.getUsername());
    }

}