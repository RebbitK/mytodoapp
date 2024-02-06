package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.InfoResponseDto;
import com.sparta.mytodoapp.dto.SignupRequestDto;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.jwt.JwtUtil;
import com.sparta.mytodoapp.repository.UserRepository;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sparta.mytodoapp.entity.UserRoleEnum.USER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "admin";

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 기본 설정
        UserRoleEnum role = USER;

        // 사용자 등록
        User user = new User(username, password, role);
        userRepository.save(user);
    }


    public InfoResponseDto info(UserDetailsImpl userDetails) {;
        return new InfoResponseDto(userDetails.getUsername(), userDetails.getUser().getRole());
    }
}
