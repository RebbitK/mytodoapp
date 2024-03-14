package com.sparta.mytodoapp.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginInfo {

	private Long userId;

	private String username;

	private String password;

}
