package com.sparta.mytodoapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Size(min = 4, max = 10, message = "아이디는4자 이상 10자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "아이디는 알파벳 소문자와 숫자로 구성할 수 있습니다.")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "비밀번호는 알파벳과 숫자로 구성할 수 있습니다.")
    private String password;
    private boolean admin = false;
}