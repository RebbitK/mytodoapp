package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.entity.CommentEntity;
import com.sparta.mytodoapp.entity.UserEntity;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CommentEntityRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    private CommentEntity testComment(){
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        UserEntity userEntity = new UserEntity(username,password,role);
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        return new CommentEntity(commentRequestDto, userEntity);
    }
    @Test
    @DisplayName("댓글 저장 테스트")
    void save() {
        //given
        CommentEntity commentEntity = testComment();
        //when
        CommentEntity testCommentEntity = commentRepository.save(commentEntity);
        //then
        assertEquals(commentEntity, testCommentEntity);
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void delete() {
        //given
        CommentEntity commentEntity = testComment();
        commentRepository.save(commentEntity);
        //when
        commentRepository.delete(commentEntity);
        //then
        assertTrue(commentRepository.findById(commentEntity.getId()).isEmpty());
    }

}