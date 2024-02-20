package com.sparta.mytodoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleRequestDto {
    private String title;
    private String text;

}
