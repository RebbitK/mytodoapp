package com.sparta.mytodoapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.sparta.mytodoapp.dto.InfoResponseDto;
import com.sparta.mytodoapp.dto.SignupRequestDto;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.repository.JpaUserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

	@Mock
	JpaUserRepository jpaUserRepository;

	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	UserServiceImpl userService;

	@Test
	@DisplayName("회원가입 테스트")
	void signupTest() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();
		signupRequestDto.setUsername("Test");
		signupRequestDto.setPassword("12345678");
		//when
		Boolean response = userService.signup(signupRequestDto);
		//then
		assertNotNull(response);
		assertEquals(response, true);
	}

	@Test
	@DisplayName("회원가입 실패 테스트")
	void signupFailTest() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();
		signupRequestDto.setUsername("Test");
		signupRequestDto.setPassword("12345678");
		User user = new User("Test", "12345678", UserRoleEnum.USER);
		given(jpaUserRepository.findByUsername(signupRequestDto.getUsername())).willReturn(
			Optional.of(user));
		//when
		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> userService.signup(signupRequestDto));
		//then
		assertEquals(exception.getMessage(), "중복된 이름을 가진 회원이 있습니다.");
	}

	@Test
	@DisplayName("정보 조회 테스트")
	void infoTest() {
		//given
		User user = new User("Info", "12345678", UserRoleEnum.USER);
		//when
		InfoResponseDto response = userService.info(user);
		//then
		assertNotNull(response);
		assertEquals(response.getUsername(), user.getUsername());
	}

}