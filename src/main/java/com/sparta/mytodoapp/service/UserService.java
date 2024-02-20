package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.CommonResponse;
import com.sparta.mytodoapp.dto.InfoResponseDto;
import com.sparta.mytodoapp.dto.SignupRequestDto;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.repository.UserRepository;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sparta.mytodoapp.entity.UserRoleEnum.USER;
import static com.sparta.mytodoapp.service.StatusCheck.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "admin";

    public ResponseEntity<CommonResponse<?>> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            return badRequest("중복된 회원명 입니다.");
        }

        // 사용자 ROLE 기본 설정
        UserRoleEnum role = USER;

        // 사용자 등록
        User user = new User(username, password, role);
        userRepository.save(user);
        return success("회원가입에 성공하셨습니다.","");
    }


    public ResponseEntity<?> info(UserDetailsImpl userDetails) {;
        return success("정보 조회에 성공하셨습니다.",new InfoResponseDto(userDetails.getUsername(), userDetails.getUser().getRole()));
    }
}
