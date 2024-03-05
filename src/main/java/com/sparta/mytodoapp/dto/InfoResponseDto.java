package com.sparta.mytodoapp.dto;

import com.sparta.mytodoapp.entity.User;
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

    public InfoResponseDto(User user){
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
