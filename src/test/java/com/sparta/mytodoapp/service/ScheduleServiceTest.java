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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ScheduleServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ScheduleRepository mockScheduleRepository;
    @InjectMocks //스케쥴 서비스 안에다가 mock으로 해준 의존성 주입 자동으로 주입 위에  ScheduleRepository mockScheduleRepository; UserRepository userRepository;
    ScheduleService scheduleService;

    private Schedule testSchedule() {
        return new Schedule(new ScheduleRequestDto("제목", "내용"), testUser());
    }

    private User testUser() {
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        return new User(username, password, role);
    }

    @Test
    @DisplayName("할일카드 만들기 테스트")
    void createSchedule() {
        //given
        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("제목", "내용");
        User user = testUser();
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.createSchedule(scheduleRequestDto, user);
        //then
        ScheduleResponseDto scheduleResponseDto = (ScheduleResponseDto) response.getBody().getData();
        assertEquals(scheduleResponseDto.getText(),scheduleRequestDto.getText());
        assertEquals(scheduleResponseDto.getTitle(),scheduleRequestDto.getTitle());
        assertEquals(response.getBody().getMsg(), "할일카드 작성에 성공하셨습니다.");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("할일카드 전체조회 테스트")
    void getSchedules() {
        //given
        List<Schedule> schedules = new ArrayList<>();
        when(mockScheduleRepository.findAllByOrderByModifiedAtDesc()).thenReturn(schedules); //given은 어긋
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.getSchedules();
        //then
        List<ScheduleResponseDto> scheduleResponseDtos = (List<ScheduleResponseDto>) response.getBody().getData();
        for (int i = 0; i < schedules.size(); i++) {
            assertEquals(scheduleResponseDtos.get(i).getText(), schedules.get(i).getText());
            assertEquals(scheduleResponseDtos.get(i).getTitle(), schedules.get(i).getTitle());
        }
        assertEquals(response.getBody().getMsg(), "할일카드 전체 조회에 성공하셨습니다.");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("자신의 할일카드 조회 테스트")
    void getMtSchedules() {
        //given
        User user = testUser();
        Schedule schedule = testSchedule();
        user.getSchedules().add(schedule);
        when(mockScheduleRepository.findByUsername(user.getUsername())).thenReturn(user.getSchedules());
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.getMySchedules(user);
        //then
        List<ScheduleResponseDto> scheduleResponseDtos = (List<ScheduleResponseDto>) response.getBody().getData();
        for (int i = 0; i < user.getSchedules().size(); i++) {
            assertEquals(scheduleResponseDtos.get(i).getText(), user.getSchedules().get(i).getText());
            assertEquals(scheduleResponseDtos.get(i).getTitle(), user.getSchedules().get(i).getTitle());
        }
        assertEquals(response.getBody().getMsg(), "나의 할일카드 조회에 성공하셨습니다.");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("선택한 할일카드 조회 테스트")
    void getSchedule() {
        //given
        Schedule schedule = testSchedule();
        when(mockScheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.getSchedule(schedule.getId());
        //then
        ScheduleResponseDto scheduleResponseDto = (ScheduleResponseDto) response.getBody().getData();
        assertEquals(scheduleResponseDto.getTitle(), schedule.getTitle());
        assertEquals(scheduleResponseDto.getText(), schedule.getText());
        assertEquals(response.getBody().getMsg(), "선택하신 할일카드 조회에 성공하셨습니다.");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("선택한 할일카드 조회 실패 테스트")
    void getScheduleFail() {
        //given
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.getSchedule(10L);
        //then
        assertEquals(response.getBody().getMsg(), "선택하신 할일카드는 존재하지 않습니다.");
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("할일 카드 수정 테스트")
    void updateSchedule() {
        //given
        User user = testUser();
        Schedule schedule = testSchedule();
        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("수정제목", "수정내용");
        given(mockScheduleRepository.findById(schedule.getId())).willReturn(Optional.of(schedule));
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.updateSchedule(schedule.getId(), user, scheduleRequestDto);
        //then
        ScheduleResponseDto scheduleResponseDto = (ScheduleResponseDto) response.getBody().getData();
        assertEquals(scheduleResponseDto.getTitle(), scheduleRequestDto.getTitle());
        assertEquals(scheduleResponseDto.getText(), scheduleRequestDto.getText());
        assertEquals(response.getBody().getMsg(), "할일카드를 수정하셨습니다.");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("할일 카드 수정 실패 - badRequest 테스트")
    void updateScheduleFail_badRequest(){
        //given
        User user = testUser();
        Schedule schedule = testSchedule();
        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("수정제목", "수정내용");
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.updateSchedule(schedule.getId(), user, scheduleRequestDto);
        //then
        assertEquals(response.getBody().getMsg(),"선택하신 할일카드는 존재하지 않습니다.");
        assertEquals(response.getStatusCode(),HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("할일 카드 수정 실패 - forBidden 테스트")
    void updateScheduleFail_forBidden(){
        //given
        User user = new User();
        Schedule schedule = testSchedule();
        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("수정제목", "수정내용");
        when(mockScheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.updateSchedule(schedule.getId(), user, scheduleRequestDto);
        //then
        assertEquals(response.getBody().getMsg(),"선택하신 할일카드를 수정하실 권한이 없습니다.");
        assertEquals(response.getStatusCode(),HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("할일카드 삭제 테스트")
    void deleteSchedule() {
        //given
        User user = testUser();
        Schedule schedule = testSchedule();
        when(mockScheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.deleteSchedule(schedule.getId(), user);
        //then
        assertEquals(response.getBody().getMsg(), "삭제에 성공하셨습니다.");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("할일 카드 삭제 실패 - badRequest 테스트")
    void deleteScheduleFail_badRequest(){
        //given
        User user = testUser();
        Schedule schedule = testSchedule();
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.deleteSchedule(schedule.getId(),user);
        //then
        assertEquals(response.getBody().getMsg(),"선택하신 할일카드는 존재하지 않습니다.");
        assertEquals(response.getStatusCode(),HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("할일 카드 삭제 실패 - forBidden 테스트")
    void deleteScheduleFail_forBidden(){
        //given
        User user = new User();
        Schedule schedule = testSchedule();
        when(mockScheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.deleteSchedule(schedule.getId(),user);
        //then
        assertEquals(response.getBody().getMsg(),"선택하신 할일카드를 삭제하실 권한이 없습니다.");
        assertEquals(response.getStatusCode(),HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("할일카드 완료 테스트")
    void completeSchedule() {
        //given
        User user = testUser();
        Schedule schedule = testSchedule();
        when(mockScheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.completeSchedule(schedule.getId(), user);
        //then
        ScheduleResponseDto scheduleResponseDto = (ScheduleResponseDto) response.getBody().getData();
        assertTrue(scheduleResponseDto.getComplete());
        assertEquals(response.getBody().getMsg(), "할일카드를 완료하셨습니다.");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    @Test
    @DisplayName("할일 카드 완료 실패 - 없는 할일 카드 테스트")
    void completeScheduleFail_badRequest1(){
        //given
        User user = testUser();
        Schedule schedule = testSchedule();
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.completeSchedule(schedule.getId(),user);
        //then
        assertEquals(response.getBody().getMsg(),"선택하신 할일카드는 존재하지 않습니다.");
        assertEquals(response.getStatusCode(),HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("할일 카드 완료 실패 - forBidden 테스트")
    void completeScheduleFail_forBidden(){
        //given
        User user = new User();
        Schedule schedule = testSchedule();
        when(mockScheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.completeSchedule(schedule.getId(),user);
        //then
        assertEquals(response.getBody().getMsg(),"선택하신 할일카드를 완료하실 권한이 없습니다.");
        assertEquals(response.getStatusCode(),HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("할일 카드 완료 실패 - 이미 완료된 할일 카드 테스트")
    void completeScheduleFail_badRequest12(){
        //given
        User user = testUser();
        Schedule schedule = testSchedule();
        schedule.setComplete(true);
        when(mockScheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        //when
        ResponseEntity<CommonResponse<?>> response = scheduleService.completeSchedule(schedule.getId(),user);
        //then
        assertEquals(response.getBody().getMsg(),"이미 완료한 작업입니다.");
        assertEquals(response.getStatusCode(),HttpStatus.BAD_REQUEST);
    }
}