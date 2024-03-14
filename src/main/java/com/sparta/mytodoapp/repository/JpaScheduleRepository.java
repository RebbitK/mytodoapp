package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaScheduleRepository extends JpaRepository<ScheduleEntity, Long>, ScheduleRepository {

}
