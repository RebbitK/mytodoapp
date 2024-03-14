package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.UserEntity;
import com.sparta.mytodoapp.projection.LoginInfo;
import java.util.Optional;

public interface UserRepository {

	Optional<UserEntity> findByUsername(String username);

	Optional<LoginInfo> findByUsernameAndPassword(String username);
}
