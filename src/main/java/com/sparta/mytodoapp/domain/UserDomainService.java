package com.sparta.mytodoapp.domain;

import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserDomainService {
    private final UserRepository userRepository;

    public Optional<User> findUser(String username){
        return userRepository.findByUsername(username);
    }
}
