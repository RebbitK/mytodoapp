package com.sparta.mytodoapp.dto;

import com.sparta.mytodoapp.entity.CommentEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentEntityResponseDtoTest {

    @Test
    @DisplayName("댓글 responseDto 생성 테스트")
    void createCommentResponseDto() {
        // given
        Long id = 1L;
        String commentText = "댓글";
        String username = "test_user";
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(id);
        commentEntity.setComment(commentText);
        commentEntity.setUsername(username);

        // when
        CommentResponseDto responseDto = new CommentResponseDto(commentEntity);
        // then
        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(commentText, responseDto.getComment());
        assertEquals(username, responseDto.getUsername());
    }

}