package com.sparta.mytodoapp.service;

import static com.sparta.mytodoapp.entity.UserRoleEnum.USER;

import com.sparta.mytodoapp.dto.InfoResponseDto;
import com.sparta.mytodoapp.dto.SignupRequestDto;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "admin";

    @Override
    public Boolean signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        if(userRepository.findByUsername(username).isPresent()){
			throw new IllegalArgumentException("중복된 이름을 가진 회원이 있습니다.");
        }
        // 사용자 ROLE 기본 설정
        UserRoleEnum role = USER;

        // 사용자 등록
        User user = new User(username, password, role);
        userRepository.save(user);
        return true;
    }


    @Override
    public InfoResponseDto info(User user) {;
        return new InfoResponseDto(user);
    }
}
