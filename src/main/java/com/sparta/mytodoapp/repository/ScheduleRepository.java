package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.Schedule;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    @Query("select s from Schedule s left join fetch s.comments")
    Optional<List<Schedule>> findByUsername(String username);

    @Query("select s from Schedule s left join fetch s.comments")
    Optional<List<Schedule>> findAllByOrderByModifiedAtDesc();
}
