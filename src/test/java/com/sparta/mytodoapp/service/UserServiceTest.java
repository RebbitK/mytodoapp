package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.CommonResponse;
import com.sparta.mytodoapp.dto.SignupRequestDto;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    UserService userService;
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;
    @Test
    @DisplayName("회원가입 테스트")
    void signupTest(){
        //given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("Test");
        signupRequestDto.setPassword("12345678");
        //when
        ResponseEntity<CommonResponse<?>> response = userService.signup(signupRequestDto);
        //then
        assertNotNull(response);
        assertEquals(response.getBody().getMsg(),"회원가입에 성공하셨습니다.");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("회원가입 실패 테스트")
    void signupFailTest(){
        //given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("Test");
        signupRequestDto.setPassword("12345678");
        User user = new User("Test","12345678",UserRoleEnum.USER);
        UserService service = new UserService(userRepository,passwordEncoder);
        given(userRepository.findByUsername(signupRequestDto.getUsername())).willReturn(Optional.of(user));
        //when
        ResponseEntity<CommonResponse<?>> response = service.signup(signupRequestDto);
        //then
        assertEquals(response.getBody().getMsg(),"중복된 회원명 입니다.");
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("정보 조회 테스트")
    void infoTest(){
        //given
        User user = new User("Info","12345678", UserRoleEnum.USER);
        //when
        ResponseEntity<CommonResponse<?>> response = userService.info(user);
        //then
        assertNotNull(response);
        assertEquals(response.getBody().getMsg(),"정보 조회에 성공하셨습니다.");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

}