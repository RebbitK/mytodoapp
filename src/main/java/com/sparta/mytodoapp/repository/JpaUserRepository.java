package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long>,UserRepository {

}
