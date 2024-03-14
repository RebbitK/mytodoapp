package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>,UserRepository {

}
