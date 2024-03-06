package com.sparta.mytodoapp.repository;

import com.sparta.mytodoapp.config.AuditingConfig;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.entity.Schedule;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(AuditingConfig.class)
class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository scheduleRepository;

    private Schedule testSchedule(){
        ScheduleRequestDto requestDto = new ScheduleRequestDto("제목", "내용");
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        User user = new User(username,password,role);
        return new Schedule(requestDto,user);
    }

//    @Test
//    @DisplayName("할일카드 유저 이름 검색 테스트")
//    void findByUsername() {
//        //given
//        User user = new User("Test","12345678",UserRoleEnum.USER);
//        Schedule schedule1 = new Schedule(new ScheduleRequestDto("제목1","내용1"),user);
//        Schedule schedule2 = new Schedule(new ScheduleRequestDto("제목2","내용2"),user);
//        List<Schedule> schedules = new ArrayList<>();
//        schedules.add(schedule1);
//        schedules.add(schedule2);
//        scheduleRepository.save(schedule1);
//        scheduleRepository.save(schedule2);
//        //when
//        List<Schedule> testSchedule = scheduleRepository.findByUsername(user.getUsername());
//        //then
//        assertEquals(schedules,testSchedule);
//    }
//
//    @Test
//    @DisplayName("할일카드 작성된 순서 내림차순 검색 테스트")
//    void findAllByOrderByModifiedAtDesc() {
//        //given
//        User user = new User("Test","12345678",UserRoleEnum.USER);
//        Schedule schedule1 = new Schedule(new ScheduleRequestDto("제목1","내용1"),user);
//        Schedule schedule2 = new Schedule(new ScheduleRequestDto("제목2","내용2"),user);
//        List<Schedule> schedules = new ArrayList<>();
//        schedules.add(schedule1);
//        schedules.add(schedule2);
//        scheduleRepository.save(schedule1);
//        scheduleRepository.save(schedule2);
//        //when
//        List<Schedule> testSchedule = scheduleRepository.findAllByOrderByModifiedAtDesc();
//        //then
//        assertEquals(schedules,testSchedule);
//    }
//
//    @Test
//    @DisplayName("할일카드 저장 테스트")
//    void save() {
//        //given
//        Schedule schedule = testSchedule();
//        //when
//        Schedule testSchedule = scheduleRepository.save(schedule);
//        //then
//        assertEquals(schedule,testSchedule);
//    }
//
//    @Test
//    @DisplayName("할일카드 삭제 테스트")
//    void delete() {
//        //given
//        Schedule schedule = testSchedule();
//        scheduleRepository.save(schedule);
//        //when
//        scheduleRepository.delete(schedule);
//        //then
//        assertTrue(scheduleRepository.findById(schedule.getId()).isEmpty());
//    }
}