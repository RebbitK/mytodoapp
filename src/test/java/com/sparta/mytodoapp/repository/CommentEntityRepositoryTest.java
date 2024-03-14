package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.config.TestConfig;
import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.entity.CommentEntity;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import com.sparta.mytodoapp.entity.UserEntity;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class CommentEntityRepositoryTest {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    JpaUserRepository jpaUserRepository;

    private CommentEntity testComment(){
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        UserEntity userEntity = new UserEntity(username,password,role);
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(10L);
        scheduleEntity.setUsername("Test");
        jpaUserRepository.save(userEntity);
        return new CommentEntity(commentRequestDto, userEntity,scheduleEntity);
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