package com.sparta.mytodoapp.controller;

import com.sparta.mytodoapp.dto.CommonResponse;
import com.sparta.mytodoapp.dto.SignupRequestDto;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import com.sparta.mytodoapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<CommonResponse<?>> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @GetMapping("/user/info")
    public ResponseEntity<CommonResponse<?>> info(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.info(userDetails.getUser());
    }

}
