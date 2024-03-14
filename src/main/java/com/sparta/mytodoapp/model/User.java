package com.sparta.mytodoapp.model;

import com.sparta.mytodoapp.dto.InfoResponseDto;
import com.sparta.mytodoapp.entity.UserEntity;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long id;
	private String username;
	private String password;
	private UserRoleEnum role;

	public static User from(UserEntity userEntity){
		return new User(
			userEntity.getId(),
			userEntity.getUsername(),
			userEntity.getPassword(),
			userEntity.getRole()
		);
	}

	public UserEntity toEntity(){
		return new UserEntity(
			id,
			username,
			password,
			role
		);
	}

	public InfoResponseDto responseDto(){
		return new InfoResponseDto(
			username,
			role
		);
	}

}
