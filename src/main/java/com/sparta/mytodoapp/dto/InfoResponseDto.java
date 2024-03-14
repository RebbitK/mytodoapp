package com.sparta.mytodoapp.dto;

import com.sparta.mytodoapp.entity.UserEntity;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InfoResponseDto {
    private String username;
    private UserRoleEnum role;
}
