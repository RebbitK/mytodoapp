package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.entity.Comment;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    private Comment testComment(){
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        User user = new User(username,password,role);
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        return new Comment(commentRequestDto,user);
    }
    @Test
    @DisplayName("댓글 저장 테스트")
    void save() {
        //given
        Comment comment = testComment();
        //when
        Comment testComment = commentRepository.save(comment);
        //then
        assertEquals(comment,testComment);
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void delete() {
        //given
        Comment comment = testComment();
        commentRepository.save(comment);
        //when
        commentRepository.delete(comment);
        //then
        assertTrue(commentRepository.findById(comment.getId()).isEmpty());
    }

}