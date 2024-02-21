package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.CommonResponse;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.dto.ScheduleResponseDto;
import com.sparta.mytodoapp.entity.Schedule;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.repository.ScheduleRepository;
import com.sparta.mytodoapp.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ScheduleServiceTest {
    @Mock
    UserRepository userRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Mock
    ScheduleRepository mockScheduleRepository;

    private Schedule testSchedule(){
        return new Schedule(new ScheduleRequestDto("제목","내용"),testUser());
    }

    private User testUser(){
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        return new User(username,password,role);
    }

    @Test
    @DisplayName("할일카드 만들기 테스트")
    void createSchedule() {
        //given
        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("제목","내용");
        ScheduleService scheduleService = new ScheduleService(userRepository,scheduleRepository);
        User user = testUser();
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.createSchedule(scheduleRequestDto,user);
        //then
        assertEquals(response.getBody().getMsg(),"할일카드 작성에 성공하셨습니다.");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("할일카드 전체조회 테스트")
    void getSchedules() {
        //given
        ScheduleService scheduleService = new ScheduleService(userRepository,scheduleRepository);
        Schedule schedule1 = new Schedule();
        schedule1.setUsername("Test1");
        schedule1.setText("Text1");
        schedule1.setTitle("Title1");
        Schedule schedule2 = new Schedule();
        schedule2.setUsername("Test2");
        schedule2.setText("Text2");
        schedule2.setTitle("Title2");
        List<ScheduleResponseDto> schedules = new ArrayList<>();
        schedules.add(new ScheduleResponseDto(schedule1));
        schedules.add(new ScheduleResponseDto(schedule2));
        scheduleRepository.save(schedule2);
        scheduleRepository.save(schedule1);
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.getSchedules();
        //then
        List<ScheduleResponseDto> scheduleResponseDtos = (List<ScheduleResponseDto>) response.getBody().getData();
        for(int i=0;i<schedules.size();i++){
            assertEquals(scheduleResponseDtos.get(i).getText(),schedules.get(i).getText());
            assertEquals(scheduleResponseDtos.get(i).getTitle(),schedules.get(i).getTitle());
        }
    }

    @Test
    @DisplayName("자신의 할일카드 조회 테스트")
    void getMtSchedules() {
        //given
        ScheduleService scheduleService = new ScheduleService(userRepository,scheduleRepository);
        User user = testUser();
        Schedule schedule = testSchedule();
        user.getSchedules().add(schedule);
        scheduleRepository.save(schedule);
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.getMySchedules(user);
        //then
        List<ScheduleResponseDto> scheduleResponseDtos = (List<ScheduleResponseDto>) response.getBody().getData();
        for(int i=0;i<user.getSchedules().size();i++){
            assertEquals(scheduleResponseDtos.get(i).getText(),user.getSchedules().get(i).getText());
            assertEquals(scheduleResponseDtos.get(i).getTitle(),user.getSchedules().get(i).getTitle());
        }
    }

    @Test
    @DisplayName("선택한 할일카드 조회 테스트")
    void getSchedule() {
        //given
        ScheduleService scheduleService = new ScheduleService(userRepository,scheduleRepository);
        Schedule schedule = testSchedule();
        scheduleRepository.save(schedule);
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.getSchedule(1L);
        //then
        ScheduleResponseDto scheduleResponseDto = (ScheduleResponseDto) response.getBody().getData();
        assertEquals(scheduleResponseDto.getTitle(),schedule.getTitle());
        assertEquals(scheduleResponseDto.getText(),schedule.getText());
    }

    @Test
    @DisplayName("할일 카드 수정 테스트")
    void updateSchedule() {
        //given
        ScheduleService scheduleService = new ScheduleService(userRepository,mockScheduleRepository);
        User user = testUser();
        Schedule schedule = testSchedule();
        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("수정제목","수정내용");
        scheduleRepository.save(schedule);
        given(mockScheduleRepository.findById(schedule.getId())).willReturn(Optional.of(schedule));
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.updateSchedule(schedule.getId(),user,scheduleRequestDto);
        //then
        ScheduleResponseDto scheduleResponseDto = (ScheduleResponseDto) response.getBody().getData();
        assertEquals(scheduleResponseDto.getTitle(),scheduleRequestDto.getTitle());
        assertEquals(scheduleResponseDto.getText(),scheduleRequestDto.getText());
    }

    @Test
    @DisplayName("할일카드 삭제 테스트")
    void deleteSchedule() {
        //given
        ScheduleService scheduleService = new ScheduleService(userRepository,scheduleRepository);
        User user = testUser();
        Schedule schedule = testSchedule();
        scheduleRepository.save(schedule);
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.deleteSchedule(schedule.getId(), user);
        //then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getMsg(), "삭제에 성공하셨습니다.");
        assertFalse(scheduleRepository.findById(schedule.getId()).isPresent());
    }

    @Test
    @DisplayName("할일카드 완료 테스트")
    void completeSchedule() {
        //given
        ScheduleService scheduleService = new ScheduleService(userRepository,scheduleRepository);
        User user = testUser();
        Schedule schedule = testSchedule();
        scheduleRepository.save(schedule);
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.completeSchedule(schedule.getId(),user);
        //then
        ScheduleResponseDto scheduleResponseDto = (ScheduleResponseDto) response.getBody().getData();
        assertTrue(scheduleResponseDto.getComplete());
    }
}