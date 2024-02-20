package com.sparta.mytodoapp.controller;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.dto.CommentResponseDto;
import com.sparta.mytodoapp.dto.CommonResponse;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import com.sparta.mytodoapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{id}")
    public ResponseEntity<CommonResponse<?>> createComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(id, userDetails.getUser(), requestDto);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommonResponse<?>> updateComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, userDetails.getUser(), requestDto);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommonResponse<?>> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails.getUser());
    }
}
