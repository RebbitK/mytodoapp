package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.entity.Schedule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepository {

}
