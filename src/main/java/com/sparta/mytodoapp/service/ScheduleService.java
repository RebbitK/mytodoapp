package com.sparta.mytodoapp.service;


import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.dto.ScheduleResponseDto;
import com.sparta.mytodoapp.dto.StateResponseDto;
import com.sparta.mytodoapp.entity.Schedule;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.repository.ScheduleRepository;
import com.sparta.mytodoapp.repository.UserRepository;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, User user) {
        Schedule schedule = new Schedule(requestDto,user);
        user.getSchedules().add(schedule);
        scheduleRepository.save(schedule);
        userRepository.save(user);
        return new ScheduleResponseDto(schedule);
    }

    public List<ScheduleResponseDto> getSchedules() {
        List<Schedule> schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
        return changeResponseDto(schedules);
    }

    public List<ScheduleResponseDto> getMtSchedules(UserDetailsImpl userDetails) {
        List<Schedule> schedules = scheduleRepository.findByUsername(userDetails.getUsername());
        return changeResponseDto(schedules);
    }


    public ScheduleResponseDto getSchedule(Long id) {
        return new ScheduleResponseDto(scheduleRepository.findById(id).orElseThrow(NullPointerException::new));
    }

    @Transactional
    public ResponseEntity<?> updateSchedule(Long id, UserDetailsImpl userDetails,ScheduleRequestDto requestDto) {
        Object object = findSchedule(id,userDetails).getBody();
        if(object instanceof StateResponseDto){
            return ResponseEntity.status(400).body(object);
        }
        Schedule schedule = (Schedule) object;
        assert schedule != null;
        schedule.update(requestDto);
        return ResponseEntity.ok(new ScheduleResponseDto(schedule));
    }

    @Transactional
    public ResponseEntity<?> deleteSchedule(Long id, UserDetailsImpl userDetails) {
        Object object = findSchedule(id,userDetails).getBody();
        if(object instanceof StateResponseDto){
            return ResponseEntity.status(400).body(object);
        }
        Schedule schedule = (Schedule) object;
        assert schedule != null;
        scheduleRepository.delete(schedule);
        return ResponseEntity.ok("삭제하였습니다.");
    }


    @Transactional
    public ResponseEntity<?> completeSchedule(Long id, UserDetailsImpl userDetails) {
        Object object = findSchedule(id,userDetails).getBody();
        if(object instanceof StateResponseDto){
            return ResponseEntity.status(400).body(object);
        }
        Schedule schedule = (Schedule) object;
        assert schedule != null;
        if(schedule.getComplete()){
            throw new IllegalArgumentException("이미 완료된 작업입니다.");
        }
        schedule.setComplete(true);

        return ResponseEntity.ok(new ScheduleResponseDto(schedule));
    }


    private List<ScheduleResponseDto> changeResponseDto(List<Schedule> schedules){
        List<ScheduleResponseDto> responseDtoList= new ArrayList<>();
        for(Schedule schedule: schedules){
            responseDtoList.add(new ScheduleResponseDto(schedule));
        }
        return  responseDtoList;
    }

    private ResponseEntity<?> findSchedule(Long id, UserDetailsImpl userDetails){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당하는 일정이 없습니다.")
                );
        User user = userDetails.getUser();
        if(!schedule.getUsername().equals(user.getUsername())){
            return ResponseEntity.status(400).body(new StateResponseDto("400","권한이 없습니다."));
        }
        return ResponseEntity.ok(schedule);
    }

}
