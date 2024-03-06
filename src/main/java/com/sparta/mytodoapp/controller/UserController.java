package com.sparta.mytodoapp.controller;

import com.sparta.mytodoapp.dto.CommonResponse;
import com.sparta.mytodoapp.dto.InfoResponseDto;
import com.sparta.mytodoapp.dto.SignupRequestDto;
import com.sparta.mytodoapp.exception.RestApiException;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import com.sparta.mytodoapp.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

	private final UserServiceImpl userServiceImpl;

	@PostMapping("/user/signup")
	public ResponseEntity<CommonResponse<Boolean>> signup(
		@Valid @RequestBody SignupRequestDto requestDto) {
		Boolean signup = userServiceImpl.signup(requestDto);

		return ResponseEntity.ok()
			.body(CommonResponse.<Boolean>builder()
				.msg("회원가입에 성공하셨습니다")
				.statusCode(HttpStatus.OK.value())
				.data(signup)
				.build());
	}

	@GetMapping("/user/info")
	public ResponseEntity<CommonResponse<InfoResponseDto>> info(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		InfoResponseDto responseDto = userServiceImpl.info(userDetails.getUser());
		return ResponseEntity.ok()
			.body(CommonResponse.<InfoResponseDto>builder()
				.msg("정보 조회에 성공하셨습니다.")
				.statusCode(HttpStatus.OK.value())
				.data(responseDto)
				.build());
	}

//	@ExceptionHandler({IllegalArgumentException.class})
//	public ResponseEntity<RestApiException> badRequest(IllegalArgumentException e) {
//		RestApiException restApiException = new RestApiException(e.getMessage(),
//			HttpStatus.BAD_REQUEST.value());
//		return new ResponseEntity<>(
//			restApiException,
//			HttpStatus.BAD_REQUEST
//		);
//	}

}
