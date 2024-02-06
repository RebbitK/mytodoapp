package com.sparta.mytodoapp.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class StateResponseDto {
    private String state;
    private String msg;

    public StateResponseDto(String state, String msg) {
        this.state = state;
        this.msg = msg;
    }
}
