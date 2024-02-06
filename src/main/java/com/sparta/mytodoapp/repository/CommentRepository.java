package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment,Long> {
}
