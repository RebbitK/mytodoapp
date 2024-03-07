package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.Schedule;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ScheduleRepository {

	Optional<Page<Schedule>> findByUsername(String username, Pageable pageable);

	Optional<Page<Schedule>> findAllByOrderByModifiedAtDesc(Pageable pageable);

}
