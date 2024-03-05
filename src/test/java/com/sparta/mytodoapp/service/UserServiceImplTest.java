package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userServiceImpl;

//    @Test
//    @DisplayName("회원가입 테스트")
//    void signupTest(){
//        //given
//        SignupRequestDto signupRequestDto = new SignupRequestDto();
//        signupRequestDto.setUsername("Test");
//        signupRequestDto.setPassword("12345678");
//        //when
//        ResponseEntity<CommonResponse<?>> response = userService.signup(signupRequestDto);
//        //then
//        assertNotNull(response);
//        assertEquals(response.getBody().getMsg(),"회원가입에 성공하셨습니다.");
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//    }
//
//    @Test
//    @DisplayName("회원가입 실패 테스트")
//    void signupFailTest(){
//        //given
//        SignupRequestDto signupRequestDto = new SignupRequestDto();
//        signupRequestDto.setUsername("Test");
//        signupRequestDto.setPassword("12345678");
//        User user = new User("Test","12345678",UserRoleEnum.USER);
//        given(userRepository.findByUsername(signupRequestDto.getUsername())).willReturn(Optional.of(user));
//        //when
//        ResponseEntity<CommonResponse<?>> response = userService.signup(signupRequestDto);
//        //then
//        assertEquals(response.getBody().getMsg(),"중복된 회원명 입니다.");
//        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    @DisplayName("정보 조회 테스트")
//    void infoTest(){
//        //given
//        User user = new User("Info","12345678", UserRoleEnum.USER);
//        //when
//        ResponseEntity<CommonResponse<?>> response = userService.info(user);
//        //then
//        assertNotNull(response);
//        assertEquals(response.getBody().getMsg(),"정보 조회에 성공하셨습니다.");
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//    }

}