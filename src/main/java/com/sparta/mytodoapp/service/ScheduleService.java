package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.dto.ScheduleResponseDto;
import com.sparta.mytodoapp.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ScheduleService {

	/**
	 * 게시글 생성
	 * @param requestDto 게시글 생성 요청정보
	 * @param user 게시글 생성 요청자
	 * @return 게시글 생성 결과
	 */
	ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, User user);

	/**
	 * 게시글 전체 조회
	 * @return 게시글 전체 조회 결과
	 */
	Page<ScheduleResponseDto> getSchedules();

	/**
	 * 나의 게시글 조회
	 * @param user 게시글 조회 요청자
	 * @return 나의 게시글 조회 결과
	 */
	Page<ScheduleResponseDto> getMySchedules(User user);

	/**
	 * 선택 게시글 조회
	 * @param id 게시글 조회 ID
	 * @return 선택 게시글 조회 결과
	 */
	ScheduleResponseDto getSchedule(Long id);

	/**
	 * 게시글 수정
	 * @param id 수정할 게시글 ID
	 * @param user 게시글 수정 요청자
	 * @param requestDto 게시글 수정 요청정보
	 * @return 게시글 수정 결과
	 */
	ScheduleResponseDto updateSchedule(Long id, User user,ScheduleRequestDto requestDto);

	/**
	 * 게시글 삭제
	 * @param id 삭제할 게시글 ID
	 * @param user 게시글 삭제 요청자
	 * @return 게시글 삭제 결과
	 */
	Boolean deleteSchedule(Long id, User user);

	/**
	 * 게시글 완료
	 * @param id 완료할 게시글 ID
	 * @param user 게시글 완료 요청자
	 * @return 게시글 완료 결과
	 */

	ScheduleResponseDto completeSchedule(Long id, User user);

}
