package com.sparta.mytodoapp.controller;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.dto.CommentResponseDto;
import com.sparta.mytodoapp.dto.CommonResponse;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import com.sparta.mytodoapp.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

	private final CommentServiceImpl commentServiceImpl;

	@PostMapping("/comments/{id}")
	public ResponseEntity<CommonResponse<CommentResponseDto>> createComment(@PathVariable Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody CommentRequestDto requestDto) {
		CommentResponseDto responseDto = commentServiceImpl.createComment(id, userDetails.getUserEntity(),
			requestDto);
		return ResponseEntity.ok()
			.body(CommonResponse.<CommentResponseDto>builder()
				.msg("댓글작성에 성공하셨습니다.")
				.statusCode(HttpStatus.OK.value())
				.data(responseDto)
				.build());
	}

	@PutMapping("/comments/{id}")
	public ResponseEntity<CommonResponse<CommentResponseDto>> updateComment(@PathVariable Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody CommentRequestDto requestDto) {
		CommentResponseDto responseDto = commentServiceImpl.updateComment(id, userDetails.getUserEntity(),
			requestDto);
		return ResponseEntity.ok()
			.body(CommonResponse.<CommentResponseDto>builder()
				.msg("댓글 수정에 성공하셨습니다.")
				.statusCode(HttpStatus.OK.value())
				.data(responseDto)
				.build());
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<CommonResponse<Boolean>> deleteComment(@PathVariable Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Boolean response = commentServiceImpl.deleteComment(id, userDetails.getUserEntity());
		return ResponseEntity.ok()
			.body(CommonResponse.<Boolean>builder()
				.msg("댓글 수정에 성공하셨습니다.")
				.statusCode(HttpStatus.OK.value())
				.data(response)
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
//
//	@ExceptionHandler({AccessDeniedException.class})
//	public ResponseEntity<RestApiException> forBidden(AccessDeniedException e) {
//		RestApiException restApiException = new RestApiException(e.getMessage(),
//			HttpStatus.FORBIDDEN.value());
//		return new ResponseEntity<>(
//			restApiException,
//			HttpStatus.FORBIDDEN
//		);
//	}
}
