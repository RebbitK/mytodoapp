package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.InfoResponseDto;
import com.sparta.mytodoapp.dto.SignupRequestDto;
import com.sparta.mytodoapp.entity.UserEntity;

public interface UserService {

	/**
	 * 회원가입
	 * @return 회원가입 결과
	 */
	Boolean signup(SignupRequestDto requestDto);

	/**
	 * 유저정보 조회
	 * @param userEntity 유저정보 요청자
	 * @return 유저정보 조회결과
	 */
	InfoResponseDto info(UserEntity userEntity);
}
