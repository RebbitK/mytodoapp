package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.entity.UserEntity;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityDetailsTest {

    @Test
    @DisplayName("유저 details 테스트")
    void userDetailsImplTest() {
        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setPassword("password123");
        userEntity.setRole(UserRoleEnum.USER);

        // When
        UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);

        // Then
        assertEquals(userEntity.getUsername(), userDetails.getUsername());
        assertEquals(userEntity.getPassword(), userDetails.getPassword());
        assertEquals(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), userDetails.getAuthorities());
    }
}