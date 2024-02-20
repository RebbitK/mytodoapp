package com.sparta.mytodoapp.controller;

import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.dto.ScheduleResponseDto;
import com.sparta.mytodoapp.entity.CommonResponse;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import com.sparta.mytodoapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return scheduleService.createSchedule(requestDto,userDetails.getUser());
    }
    //전체 할일카드 조회
    @GetMapping("/schedules")
    public ResponseEntity<?> getSchedules(){
        return scheduleService.getSchedules();
    }
    // 자신의 할일카드 조회
    @GetMapping("/my-schedules")
    public ResponseEntity<?> getMySchedules(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return scheduleService.getMtSchedules(userDetails);
    }
    //선택 할일카드 조회
    @GetMapping("/schedules/{id}")
    public ResponseEntity<?> getSchedule(@PathVariable Long id){
        return scheduleService.getSchedule(id);
    }
    //선택 할일카드 수정
    @PutMapping("/schedules/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody ScheduleRequestDto scheduleRequestDto){
        return scheduleService.updateSchedule(id,userDetails,scheduleRequestDto);
    }
    //선택 할일카드 삭제
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return scheduleService.deleteSchedule(id,userDetails);
    }
    //선택 할일카드 완료
    @GetMapping("/schedules-complee/{id}t")
    public ResponseEntity<?> completeSchedule(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return scheduleService.completeSchedule(id,userDetails);
    }

    @GetMapping("/user/test/{id}")
    public ResponseEntity<?> test(@PathVariable Long id){
        return scheduleService.test(id);
    }

}
