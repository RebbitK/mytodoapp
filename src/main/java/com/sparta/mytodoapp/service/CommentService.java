package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.dto.CommentResponseDto;
import com.sparta.mytodoapp.entity.UserEntity;

public interface CommentService {
	/**
	 * 댓글 생성
	 * @param id 댓글을 생성할 게시글의 요청정보
	 * @param userEntity 게시글 생성 요청자
	 * @param requestDto 댓글 생성 요청정보
	 * @return 댓글 생성 결과
	 */
	CommentResponseDto createComment(Long id, UserEntity userEntity, CommentRequestDto requestDto);

	/**
	 * 댓글 수정
	 * @param id 수정할 댓글의 id
	 * @param userEntity 댓글 수정 요청자
	 * @param requestDto 댓글 수정 요청정보
	 * @return 댓글 수정 결과
	 */
	CommentResponseDto updateComment(Long id, UserEntity userEntity, CommentRequestDto requestDto);

	/**
	 * 댓글 삭제
	 * @param id 삭제할 댓글의 id
	 * @param userEntity 댓글 삭제 요청자
	 * @return 댓글 삭제 결과
	 */
	Boolean deleteComment(Long id, UserEntity userEntity);


}
