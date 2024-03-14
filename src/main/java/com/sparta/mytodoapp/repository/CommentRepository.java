package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<CommentEntity,Long> {
}
