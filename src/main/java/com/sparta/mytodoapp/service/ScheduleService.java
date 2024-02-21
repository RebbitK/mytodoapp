package com.sparta.mytodoapp.service;


import com.sparta.mytodoapp.dto.CommonResponse;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.dto.ScheduleResponseDto;
import com.sparta.mytodoapp.entity.Schedule;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.repository.ScheduleRepository;
import com.sparta.mytodoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.sparta.mytodoapp.service.StatusCheck.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ResponseEntity<CommonResponse<?>> createSchedule(ScheduleRequestDto requestDto, User user) {
        Schedule schedule = new Schedule(requestDto,user);
        user.getSchedules().add(schedule);
        scheduleRepository.save(schedule);
        userRepository.save(user);
        return success("할일카드 작성에 성공하셨습니다.",new ScheduleResponseDto(schedule));
    }

    public ResponseEntity<CommonResponse<?>> getSchedules() {
        List<Schedule> schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
        return success("할일카드 전체 조회에 성공하셨습니다.",schedules.stream().map(ScheduleResponseDto::new).toList());
    }

    public ResponseEntity<CommonResponse<?>> getMySchedules(User user) {
        List<Schedule> schedules = scheduleRepository.findByUsername(user.getUsername());
        return success("나의 할일카드 조회에 성공하셨습니다.",schedules.stream().map(ScheduleResponseDto::new).toList());
    }


    public ResponseEntity<CommonResponse<?>> getSchedule(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if(schedule.isEmpty()){
            return badRequest("선택하신 할일카드는 존재하지 않습니다.");
        }
        return success("선택하신 할일카드 조회에 성공하셨습니다.",new ScheduleResponseDto(schedule.get()));
    }

    @Transactional
    public ResponseEntity<CommonResponse<?>> updateSchedule(Long id, User user,ScheduleRequestDto requestDto) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if(schedule.isEmpty()){
            return badRequest("선택하신 할일카드는 존재하지 않습니다.");
        }
        if(!Objects.equals(schedule.get().getUsername(), user.getUsername())){
            return forBidden("선택하신 할일카드를 수정하실 권한이 없습니다.");
        }
        schedule.get().update(requestDto);
        return success("할일카드를 수정하셨습니다.",new ScheduleResponseDto(schedule.get()));
    }

    @Transactional
    public ResponseEntity<CommonResponse<?>> deleteSchedule(Long id, User user) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if(schedule.isEmpty()){
            return badRequest("선택하신 할일카드는 존재하지 않습니다.");
        }
        if(!Objects.equals(schedule.get().getUsername(), user.getUsername())){
            return forBidden("선택하신 할일카드를 삭제하실 권한이 없습니다.");
        }
        scheduleRepository.delete(schedule.get());
        return success("삭제에 성공하셨습니다.","");
    }


    @Transactional
    public ResponseEntity<CommonResponse<?>> completeSchedule(Long id, User user) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if(schedule.isEmpty()){
            return badRequest("선택하신 할일카드는 존재하지 않습니다.");
        }
        if(!Objects.equals(schedule.get().getUsername(), user.getUsername())){
            return forBidden("선택하신 할일카드를 완료하실 권한이 없습니다.");
        }
        if(schedule.get().getComplete()){
            return badRequest("이미 완료한 작업입니다.");
        }
        schedule.get().setComplete(true);

        return success("할일카드를 완료하셨습니다.",new ScheduleResponseDto(schedule.get()));
    }

}
