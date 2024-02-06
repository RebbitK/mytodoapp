package com.sparta.mytodoapp.controller;

import com.sparta.mytodoapp.dto.InfoResponseDto;
import com.sparta.mytodoapp.dto.LoginRequestDto;
import com.sparta.mytodoapp.dto.SignupRequestDto;
import com.sparta.mytodoapp.dto.StateResponseDto;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import com.sparta.mytodoapp.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public StateResponseDto signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return new StateResponseDto("200", "회원가입에 성공하셨습니다.");
    }

    @GetMapping("/user/info")
    public InfoResponseDto info(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.info(userDetails);
    }

}
