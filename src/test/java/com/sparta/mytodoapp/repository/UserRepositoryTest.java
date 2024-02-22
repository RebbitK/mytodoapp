package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User testUser() {
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        return new User(username, password, role);
    }
    @Test
    @DisplayName("유저 이름으로 찾기 테스트")
    void findByUsername() {
        //given
        User user = testUser();
        userRepository.save(user);
        //when
        Optional<User> testUser = userRepository.findByUsername("Test");
        //then
        assertEquals(user,testUser.get());
    }

    @Test
    @DisplayName("유저 저장 테스트")
    void save() {
        //given
        User user = testUser();
        //when
        User testUser = userRepository.save(user);
        //then
        assertEquals(user,testUser);
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    void delete() {
        //given
        User user = testUser();
        userRepository.save(user);
        //when
        userRepository.delete(user);
        //then
        assertTrue(userRepository.findById(user.getId()).isEmpty());
    }
}