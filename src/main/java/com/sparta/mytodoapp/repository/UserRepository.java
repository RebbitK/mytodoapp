package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.projection.LoginInfo;
import java.util.Optional;

public interface UserRepository {

	Optional<User> findByUsername(String username);

	Optional<LoginInfo> findByUsernameAndPassword(String username);
}
