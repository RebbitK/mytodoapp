package com.sparta.mytodoapp.service;


import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.dto.ScheduleResponseDto;
import com.sparta.mytodoapp.entity.Schedule;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.exception.NoPermissionException;
import com.sparta.mytodoapp.repository.JpaScheduleRepository;
import com.sparta.mytodoapp.repository.ScheduleRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

	private final JpaScheduleRepository jpaScheduleRepository;


	@Override
	@Transactional
	public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, User user) {
		Schedule schedule = new Schedule(requestDto, user);
		jpaScheduleRepository.save(schedule);
		return new ScheduleResponseDto(schedule);
	}

	@Override
	public Page<ScheduleResponseDto> getSchedules() {
		Page<Schedule> schedules = jpaScheduleRepository.findAllByOrderByModifiedAtDesc(
			PageRequest.of(0, 5)).orElseThrow(() ->
			new IllegalArgumentException("현재 작성된 할일카드가 없습니다."));
		return schedules.map(ScheduleResponseDto::new);
	}

	@Override
	public Page<ScheduleResponseDto> getMySchedules(User user) {
		Page<Schedule> schedules = jpaScheduleRepository.findByUsername(user.getUsername(),
			PageRequest.of(0, 5)).orElseThrow(() ->
			new IllegalArgumentException("현재 작성하신 할일카드가 없습니다."));
		return schedules.map(ScheduleResponseDto::new);
	}

	@Override
	public ScheduleResponseDto getSchedule(Long id) {
		Schedule schedule = jpaScheduleRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 할일카드는 존재하지 않습니다."));
		return new ScheduleResponseDto(schedule);
	}

	@Override
	@Transactional
	public ScheduleResponseDto updateSchedule(Long id, User user, ScheduleRequestDto requestDto) {
		Schedule schedule = jpaScheduleRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 할일카드는 존재하지 않습니다."));
		if (!Objects.equals(schedule.getUsername(), user.getUsername())) {
			throw new NoPermissionException("선택하신 할일카드를 수정할 권한이 없습니다.");
		}
		schedule.update(requestDto);
		return new ScheduleResponseDto(schedule);
	}

	@Override
	@Transactional
	public Boolean deleteSchedule(Long id, User user) {
		Schedule schedule = jpaScheduleRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 할일카드는 존재하지 않습니다."));
		if (!Objects.equals(schedule.getUsername(), user.getUsername())) {
			throw new NoPermissionException("선택하신 할일카드를 삭제할 권한이 없습니다.");
		}
		jpaScheduleRepository.delete(schedule);
		return true;
	}

	@Override
	@Transactional
	public ScheduleResponseDto completeSchedule(Long id, User user) {
		Schedule schedule = jpaScheduleRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 할일카드는 존재하지 않습니다."));
		if (!Objects.equals(schedule.getUsername(), user.getUsername())) {
			throw new NoPermissionException("선택하신 할일카드를 완료할 권한이 없습니다.");
		}
		if (schedule.getComplete()) {
			throw new IllegalArgumentException("이미 완료한 작업입니다.");
		}
		schedule.setComplete(true);

		return new ScheduleResponseDto(schedule);
	}

}
