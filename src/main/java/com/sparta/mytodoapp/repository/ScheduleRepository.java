package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findByUsername(String username);

    List<Schedule> findAllByOrderByModifiedAtDesc();
}
