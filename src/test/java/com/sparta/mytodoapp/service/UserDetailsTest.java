package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDetailsTest {

    @Test
    void testUserDetailsImpl() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole(UserRoleEnum.USER);

        // When
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        // Then
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), userDetails.getAuthorities());
    }
}