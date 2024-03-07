package com.sparta.mytodoapp.controller;

import com.sparta.mytodoapp.dto.CommonResponse;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.dto.ScheduleResponseDto;
import com.sparta.mytodoapp.exception.RestApiException;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import com.sparta.mytodoapp.service.ScheduleServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {

	private final ScheduleServiceImpl scheduleServiceImpl;

	@PostMapping("/schedules")
	public ResponseEntity<CommonResponse<ScheduleResponseDto>> createSchedule(
		@RequestBody ScheduleRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		ScheduleResponseDto responseDto = scheduleServiceImpl.createSchedule(requestDto,
			userDetails.getUser());
		return ResponseEntity.ok()
			.body(CommonResponse.<ScheduleResponseDto>builder()
				.msg("할일카드 작성에 성공하셨습니다.")
				.statusCode(HttpStatus.OK.value())
				.data(responseDto)
				.build());
	}

	//전체 할일카드 조회
	@GetMapping("/schedules")
	public ResponseEntity<CommonResponse<Page<ScheduleResponseDto>>> getSchedules() {
		Page<ScheduleResponseDto> responseDtoList = scheduleServiceImpl.getSchedules();
		return ResponseEntity.ok()
			.body(CommonResponse.<Page<ScheduleResponseDto>>builder()
				.msg("전체 할일카드 조회에 성공하셨습니다.")
				.statusCode(HttpStatus.OK.value())
				.data(responseDtoList)
				.build());
	}

	// 자신의 할일카드 조회
	@GetMapping("/my-schedules")
	public ResponseEntity<CommonResponse<Page<ScheduleResponseDto>>> getMySchedules(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Page<ScheduleResponseDto> responseDtoList = scheduleServiceImpl.getMySchedules(
			userDetails.getUser());
		return ResponseEntity.ok()
			.body(CommonResponse.<Page<ScheduleResponseDto>>builder()
				.msg("나의 할일카드 조회에 성공하셨습니다.")
				.statusCode(HttpStatus.OK.value())
				.data(responseDtoList)
				.build());
	}

	//선택 할일카드 조회
	@GetMapping("/schedules/{id}")
	public ResponseEntity<CommonResponse<ScheduleResponseDto>> getSchedule(@PathVariable Long id) {
		ScheduleResponseDto responseDto = scheduleServiceImpl.getSchedule(id);
		return ResponseEntity.ok()
			.body(CommonResponse.<ScheduleResponseDto>builder()
				.msg(id + "번 할일카드 조회에 성공하셨습니다.")
				.statusCode(HttpStatus.OK.value())
				.data(responseDto)
				.build());
	}

	//선택 할일카드 수정
	@PutMapping("/schedules/{id}")
	public ResponseEntity<CommonResponse<ScheduleResponseDto>> updateSchedule(@PathVariable Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody ScheduleRequestDto scheduleRequestDto) {
		ScheduleResponseDto responseDto = scheduleServiceImpl.updateSchedule(id, userDetails.getUser(),
			scheduleRequestDto);
		return ResponseEntity.ok()
			.body(CommonResponse.<ScheduleResponseDto>builder()
				.msg("할일카드 수정에 성공하셨습니다.")
				.statusCode(HttpStatus.OK.value())
				.data(responseDto)
				.build());
	}

	//선택 할일카드 삭제
	@DeleteMapping("/schedules/{id}")
	public ResponseEntity<CommonResponse<Boolean>> deleteSchedule(@PathVariable Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Boolean response = scheduleServiceImpl.deleteSchedule(id, userDetails.getUser());
		return ResponseEntity.ok()
			.body(CommonResponse.<Boolean>builder()
				.msg("할일카드 삭제에 성공하셨습니다.")
				.statusCode(HttpStatus.OK.value())
				.data(response)
				.build());
	}

	//선택 할일카드 완료
	@GetMapping("/schedules-complete/{id}")
	public ResponseEntity<CommonResponse<ScheduleResponseDto>> completeSchedule(
		@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		ScheduleResponseDto responseDto = scheduleServiceImpl.completeSchedule(id,
			userDetails.getUser());
		return ResponseEntity.ok()
			.body(CommonResponse.<ScheduleResponseDto>builder()
				.msg("할일카드를 완료시켰습니다.")
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
