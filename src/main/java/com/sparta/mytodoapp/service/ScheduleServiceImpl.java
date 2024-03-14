package com.sparta.mytodoapp.service;


import com.sparta.mytodoapp.dto.GetScheduleDto;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.dto.ScheduleResponseDto;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import com.sparta.mytodoapp.entity.UserEntity;
import com.sparta.mytodoapp.exception.NoPermissionException;
import com.sparta.mytodoapp.model.Schedule;
import com.sparta.mytodoapp.repository.JpaScheduleRepository;
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
	public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto,
		UserEntity userEntity) {
		ScheduleEntity scheduleEntity = new ScheduleEntity(requestDto, userEntity);
		jpaScheduleRepository.save(scheduleEntity);
		return Schedule.from(scheduleEntity).responseDto();
	}

	@Override
	public Page<ScheduleResponseDto> getSchedules() {
		Page<ScheduleEntity> schedules = jpaScheduleRepository.findAllByOrderByModifiedAtDesc(
			PageRequest.of(0, 5)).orElseThrow(() ->
			new IllegalArgumentException("현재 작성된 할일카드가 없습니다."));
		return schedules.map(scheduleEntity -> Schedule.from(scheduleEntity).responseDto());
	}

	@Override
	public Page<ScheduleResponseDto> getMySchedules(UserEntity userEntity) {
		Page<ScheduleEntity> schedules = jpaScheduleRepository.findByUsername(
			userEntity.getUsername(),
			PageRequest.of(0, 5)).orElseThrow(() ->
			new IllegalArgumentException("현재 작성하신 할일카드가 없습니다."));
		return schedules.map(scheduleEntity -> Schedule.from(scheduleEntity).responseDto());
	}

	@Override
	public GetScheduleDto getSchedule(Long id) {
		GetScheduleDto getScheduleDto = jpaScheduleRepository.findByIdGet(id).orElseThrow(()->
			new IllegalArgumentException("선택하신 할일카드는 존재하지 않습니다."));
		return getScheduleDto;
	}

	@Override
	@Transactional
	public ScheduleResponseDto updateSchedule(Long id, UserEntity userEntity,
		ScheduleRequestDto requestDto) {
		ScheduleEntity scheduleEntity = jpaScheduleRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 할일카드는 존재하지 않습니다."));
		if (!Objects.equals(scheduleEntity.getUsername(), userEntity.getUsername())) {
			throw new NoPermissionException("선택하신 할일카드를 수정할 권한이 없습니다.");
		}
		Schedule.from(scheduleEntity).update(requestDto);
		jpaScheduleRepository.update(scheduleEntity);
		return Schedule.from(scheduleEntity).responseDto();
	}

	@Override
	@Transactional
	public Boolean deleteSchedule(Long id, UserEntity userEntity) {
		ScheduleEntity scheduleEntity = jpaScheduleRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 할일카드는 존재하지 않습니다."));
		if (!Objects.equals(scheduleEntity.getUsername(), userEntity.getUsername())) {
			throw new NoPermissionException("선택하신 할일카드를 삭제할 권한이 없습니다.");
		}
		jpaScheduleRepository.deleteSchedule(scheduleEntity);
		return true;
	}

	@Override
	@Transactional
	public ScheduleResponseDto completeSchedule(Long id, UserEntity userEntity) {
		ScheduleEntity scheduleEntity = jpaScheduleRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택하신 할일카드는 존재하지 않습니다."));
		if (!Objects.equals(scheduleEntity.getUsername(), userEntity.getUsername())) {
			throw new NoPermissionException("선택하신 할일카드를 완료할 권한이 없습니다.");
		}
		if (scheduleEntity.getComplete()) {
			throw new IllegalArgumentException("이미 완료한 작업입니다.");
		}
		scheduleEntity.setComplete(true);

		return Schedule.from(scheduleEntity).responseDto();
	}

}
