package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.UserEntity;
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
class JpaUserRepositoryTestEntity {
    @Autowired
    private JpaUserRepository jpaUserRepository;

    private UserEntity testUser() {
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        return new UserEntity(username, password, role);
    }
    @Test
    @DisplayName("유저 이름으로 찾기 테스트")
    void findByUsername() {
        //given
        UserEntity userEntity = testUser();
        jpaUserRepository.save(userEntity);
        //when
        Optional<UserEntity> testUser = jpaUserRepository.findByUsername("Test");
        //then
        assertEquals(userEntity,testUser.get());
    }

    @Test
    @DisplayName("유저 저장 테스트")
    void save() {
        //given
        UserEntity userEntity = testUser();
        //when
        UserEntity testUserEntity = jpaUserRepository.save(userEntity);
        //then
        assertEquals(userEntity, testUserEntity);
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    void delete() {
        //given
        UserEntity userEntity = testUser();
        jpaUserRepository.save(userEntity);
        //when
        jpaUserRepository.delete(userEntity);
        //then
        assertTrue(jpaUserRepository.findById(userEntity.getId()).isEmpty());
    }
}