package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ScheduleRepository {
	Page<Schedule> findByUsername(String username, Pageable pageable);

	Page<Schedule> findAllByOrderByModifiedAtDesc(Pageable pageable);

}
