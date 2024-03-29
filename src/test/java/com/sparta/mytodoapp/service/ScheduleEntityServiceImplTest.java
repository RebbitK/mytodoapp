package com.sparta.mytodoapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.sparta.mytodoapp.dto.GetScheduleDto;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.dto.ScheduleResponseDto;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import com.sparta.mytodoapp.entity.UserEntity;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.exception.NoPermissionException;
import com.sparta.mytodoapp.repository.JpaScheduleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ScheduleEntityServiceImplTest {

	@Mock
	JpaScheduleRepository mockJpaScheduleRepository;
	@InjectMocks //스케쥴 서비스 안에다가 mock으로 해준 의존성 주입 자동으로 주입 위에  ScheduleRepository mockScheduleRepository; UserRepository userRepository;
	ScheduleServiceImpl scheduleService;

	private Pageable pageable = PageRequest.of(0, 5);

	private ScheduleEntity testSchedule() {
		return new ScheduleEntity(new ScheduleRequestDto("제목", "내용"), testUser());
	}

	private List<ScheduleEntity> testSchedules() {
		List<ScheduleEntity> scheduleEntities = new ArrayList<>();
		ScheduleRequestDto requestDto1 = new ScheduleRequestDto("제목1", "내용1");
		ScheduleRequestDto requestDto2 = new ScheduleRequestDto("제목2", "내용2");
		ScheduleRequestDto requestDto3 = new ScheduleRequestDto("제목3", "내용3");
		scheduleEntities.add(new ScheduleEntity(requestDto1, testUser()));
		scheduleEntities.add(new ScheduleEntity(requestDto2, testUser()));
		scheduleEntities.add(new ScheduleEntity(requestDto3, testUser()));
		return scheduleEntities;
	}

	private UserEntity testUser() {
		String username = "Test";
		String password = "12345678";
		UserRoleEnum role = UserRoleEnum.USER;
		return new UserEntity(username, password, role);
	}

	@Test
	@DisplayName("할일카드 만들기 테스트")
	void createSchedule() {
		//given
		ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("제목", "내용");
		UserEntity userEntity = testUser();
		//when
		ScheduleResponseDto response = scheduleService.createSchedule(scheduleRequestDto,
			userEntity);
		//then
		assertEquals(response.getText(), scheduleRequestDto.getText());
		assertEquals(response.getTitle(), scheduleRequestDto.getTitle());
	}

	@Test
	@DisplayName("할일카드 전체조회 테스트")
	void getSchedules() {
		//given
		Page<ScheduleEntity> schedules = new PageImpl<>(testSchedules(), pageable, 0);
		when(mockJpaScheduleRepository.findAllByOrderByModifiedAtDesc(pageable)).thenReturn(
			Optional.of(schedules));
		//when
		Page<ScheduleResponseDto> response = scheduleService.getSchedules();
		//then
		for (int i = 0; i < schedules.getNumberOfElements(); i++) {
			assertEquals(schedules.getContent().get(i).getTitle(),
				response.getContent().get(i).getTitle());
			assertEquals(schedules.getContent().get(i).getText(),
				response.getContent().get(i).getText());
		}
	}

	@Test
	@DisplayName("자신의 할일카드 조회 테스트")
	void getMtSchedules() {
		//given
		Page<ScheduleEntity> schedules = new PageImpl<>(testSchedules(), pageable, 0);
		when(mockJpaScheduleRepository.findByUsername("Test", pageable)).thenReturn(
			Optional.of(schedules));
		//when
		Page<ScheduleResponseDto> response = scheduleService.getMySchedules(testUser());
		//then
		for (int i = 0; i < schedules.getNumberOfElements(); i++) {
			assertEquals(schedules.getContent().get(i).getTitle(),
				response.getContent().get(i).getTitle());
			assertEquals(schedules.getContent().get(i).getText(),
				response.getContent().get(i).getText());
		}
	}


	@Test
	@DisplayName("선택한 할일카드 조회 테스트")
	void getSchedule() {
		//given
		ScheduleEntity scheduleEntity = testSchedule();
		GetScheduleDto getScheduleDto = new GetScheduleDto();
		getScheduleDto.setText(scheduleEntity.getText());
		getScheduleDto.setTitle(scheduleEntity.getTitle());
		when(mockJpaScheduleRepository.findByIdGet(scheduleEntity.getId())).thenReturn(
			Optional.of(getScheduleDto));
		//when
		GetScheduleDto response = scheduleService.getSchedule(scheduleEntity.getId());
		//then
		assertEquals(response.getTitle(), scheduleEntity.getTitle());
		assertEquals(response.getText(), scheduleEntity.getText());
	}

	@Test
	@DisplayName("선택한 할일카드 조회 실패 테스트")
	void getScheduleFail() {
		//given
		//when
		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> scheduleService.getSchedule(10L));
		//then
		assertEquals(exception.getMessage(), "선택하신 할일카드는 존재하지 않습니다.");
	}

	@Test
	@DisplayName("할일 카드 수정 테스트")
	void updateSchedule() {
		//given
		UserEntity userEntity = testUser();
		ScheduleEntity scheduleEntity = testSchedule();
		ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("수정제목", "수정내용");
		when(mockJpaScheduleRepository.findById(scheduleEntity.getId())).thenReturn(
			Optional.of(scheduleEntity));
		//when
		ScheduleResponseDto response = scheduleService.updateSchedule(scheduleEntity.getId(),
			userEntity,
			scheduleRequestDto);
		//then
		assertEquals(response.getTitle(), scheduleRequestDto.getTitle());
		assertEquals(response.getText(), scheduleRequestDto.getText());
	}

	@Test
	@DisplayName("할일 카드 수정 실패 - badRequest 테스트")
	void updateScheduleFail_badRequest() {
		//given
		UserEntity userEntity = testUser();
		ScheduleEntity scheduleEntity = testSchedule();
		ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("수정제목", "수정내용");
		//when
		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> scheduleService.updateSchedule(scheduleEntity.getId(), userEntity, scheduleRequestDto));
		//then
		assertEquals(exception.getMessage(), "선택하신 할일카드는 존재하지 않습니다.");
	}

	@Test
	@DisplayName("할일 카드 수정 실패 - forBidden 테스트")
	void updateScheduleFail_forBidden() {
		//given
		UserEntity userEntity = new UserEntity();
		ScheduleEntity scheduleEntity = testSchedule();
		ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("수정제목", "수정내용");
		when(mockJpaScheduleRepository.findById(scheduleEntity.getId())).thenReturn(
			Optional.of(scheduleEntity));
		//when
		Exception exception = assertThrows(NoPermissionException.class,
			() -> scheduleService.updateSchedule(scheduleEntity.getId(), userEntity, scheduleRequestDto));
		//then
		assertEquals(exception.getMessage(), "선택하신 할일카드를 수정할 권한이 없습니다.");
	}

	@Test
	@DisplayName("할일카드 삭제 테스트")
	void deleteSchedule() {
		//given
		UserEntity userEntity = testUser();
		ScheduleEntity scheduleEntity = testSchedule();
		when(mockJpaScheduleRepository.findById(scheduleEntity.getId())).thenReturn(
			Optional.of(scheduleEntity));
		//when
		Boolean response = scheduleService.deleteSchedule(scheduleEntity.getId(), userEntity);
		//then
		assertTrue(response);
	}

	@Test
	@DisplayName("할일 카드 삭제 실패 - badRequest 테스트")
	void deleteScheduleFail_badRequest() {
		//given
		UserEntity userEntity = testUser();
		ScheduleEntity scheduleEntity = testSchedule();
		//when
		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> scheduleService.deleteSchedule(scheduleEntity.getId(), userEntity));
		//then
		assertEquals(exception.getMessage(), "선택하신 할일카드는 존재하지 않습니다.");
	}

	@Test
	@DisplayName("할일 카드 삭제 실패 - forBidden 테스트")
	void deleteScheduleFail_forBidden() {
		//given
		UserEntity userEntity = new UserEntity();
		ScheduleEntity scheduleEntity = testSchedule();
		when(mockJpaScheduleRepository.findById(scheduleEntity.getId())).thenReturn(
			Optional.of(scheduleEntity));
		//when
		Exception exception = assertThrows(NoPermissionException.class,
			() -> scheduleService.deleteSchedule(scheduleEntity.getId(), userEntity));
		//then
		assertEquals(exception.getMessage(), "선택하신 할일카드를 삭제할 권한이 없습니다.");
	}

	@Test
	@DisplayName("할일카드 완료 테스트")
	void completeSchedule() {
		//given
		UserEntity userEntity = testUser();
		ScheduleEntity scheduleEntity = testSchedule();
		when(mockJpaScheduleRepository.findById(scheduleEntity.getId())).thenReturn(
			Optional.of(scheduleEntity));
		//when
		ScheduleResponseDto response = scheduleService.completeSchedule(scheduleEntity.getId(),
			userEntity);
		//then
		assertTrue(response.getComplete());
	}

	@Test
	@DisplayName("할일 카드 완료 실패 - 없는 할일 카드 테스트")
	void completeScheduleFail_badRequest1() {
		//given
		UserEntity userEntity = testUser();
		ScheduleEntity scheduleEntity = testSchedule();
		//when
		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> scheduleService.completeSchedule(scheduleEntity.getId(), userEntity));
		//then
		assertEquals(exception.getMessage(), "선택하신 할일카드는 존재하지 않습니다.");
	}

	@Test
	@DisplayName("할일 카드 완료 실패 - forBidden 테스트")
	void completeScheduleFail_forBidden() {
		//given
		UserEntity userEntity = new UserEntity();
		ScheduleEntity scheduleEntity = testSchedule();
		when(mockJpaScheduleRepository.findById(scheduleEntity.getId())).thenReturn(
			Optional.of(scheduleEntity));
		//when
		Exception exception = assertThrows(NoPermissionException.class,
			() -> scheduleService.completeSchedule(scheduleEntity.getId(), userEntity));
		//then
		assertEquals(exception.getMessage(), "선택하신 할일카드를 완료할 권한이 없습니다.");
	}

	@Test
	@DisplayName("할일 카드 완료 실패 - 이미 완료된 할일 카드 테스트")
	void completeScheduleFail_badRequest12() {
		//given
		UserEntity userEntity = testUser();
		ScheduleEntity scheduleEntity = testSchedule();
		scheduleEntity.setComplete(true);
		when(mockJpaScheduleRepository.findById(scheduleEntity.getId())).thenReturn(
			Optional.of(scheduleEntity));
		//when
		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> scheduleService.completeSchedule(scheduleEntity.getId(), userEntity));
		//then
		assertEquals(exception.getMessage(), "이미 완료한 작업입니다.");
	}
}