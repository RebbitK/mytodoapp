package com.sparta.mytodoapp.repository;

import com.querydsl.core.Tuple;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import com.sparta.mytodoapp.model.Schedule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepository {

	Optional<Page<ScheduleEntity>> findByUsername(String username, Pageable pageable);

	Optional<Page<ScheduleEntity>> findAllByOrderByModifiedAtDesc(Pageable pageable);

	ScheduleEntity update(ScheduleEntity scheduleEntity);

	void deleteSchedule(ScheduleEntity scheduleEntity);

	Optional<ScheduleEntity> findByIdGet(Long id);

}
