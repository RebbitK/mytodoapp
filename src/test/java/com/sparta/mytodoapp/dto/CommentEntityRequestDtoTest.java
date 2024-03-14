package com.sparta.mytodoapp.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentEntityRequestDtoTest {

    @Test
    @DisplayName("댓글 생성 테스트")
    void createCommentRequestDto() {
        // given
        String comment = "댓글";
        // when
        CommentRequestDto requestDto = new CommentRequestDto(comment);
        // then
        assertNotNull(requestDto);
        assertEquals(comment, requestDto.getComment());
    }
}