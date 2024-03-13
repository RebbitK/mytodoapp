package com.sparta.mytodoapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mytodoapp.config.AuditingConfig;
import com.sparta.mytodoapp.config.TestConfig;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.entity.Schedule;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class ScheduleRepositoryTest {


    @Autowired
    private JpaScheduleRepository scheduleRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private Pageable pageable = PageRequest.of(0, 5);

    private Schedule testSchedule(){
        ScheduleRequestDto requestDto = new ScheduleRequestDto("제목", "내용");
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        User user = new User(username,password,role);
        return new Schedule(requestDto,user);
    }

    @Test
    @DisplayName("할일카드 유저 이름 검색 테스트")
    void findByUsername() {
        //given
        User user = new User("Test","12345678",UserRoleEnum.USER);
        Schedule schedule1 = new Schedule(new ScheduleRequestDto("제목1","내용1"),user);
        Schedule schedule2 = new Schedule(new ScheduleRequestDto("제목2","내용2"),user);
        userRepository.save(user);
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);
        //when
        Optional<Page<Schedule>> testSchedule = scheduleRepository.findByUsername(user.getUsername(),pageable);
        //then
        assertEquals(schedule1.getTitle(),testSchedule.get().getContent().get(0).getTitle());
        assertEquals(schedule2.getTitle(),testSchedule.get().getContent().get(1).getTitle());
    }

    @Test
    @DisplayName("할일카드 작성된 순서 내림차순 검색 테스트")
    void findAllByOrderByModifiedAtDesc() {
        //given
        User user = new User("Test","12345678",UserRoleEnum.USER);
        Schedule schedule1 = new Schedule(new ScheduleRequestDto("제목1","내용1"),user);
        Schedule schedule2 = new Schedule(new ScheduleRequestDto("제목2","내용2"),user);
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(schedule1);
        schedules.add(schedule2);
        userRepository.save(user);
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);
        //when
        Optional<Page<Schedule>> testSchedule = scheduleRepository.findAllByOrderByModifiedAtDesc(pageable);
        //then
        assertEquals(schedule1.getTitle(),testSchedule.get().getContent().get(1).getTitle());
        assertEquals(schedule2.getTitle(),testSchedule.get().getContent().get(0).getTitle());
    }

    @Test
    @DisplayName("할일카드 저장 테스트")
    void save() {
        //given
        Schedule schedule = testSchedule();
        //when
        Schedule testSchedule = scheduleRepository.save(schedule);
        //then
        assertEquals(schedule,testSchedule);
    }

    @Test
    @DisplayName("할일카드 삭제 테스트")
    void delete() {
        //given
        Schedule schedule = testSchedule();
        scheduleRepository.save(schedule);
        //when
        scheduleRepository.delete(schedule);
        //then
        assertTrue(scheduleRepository.findById(schedule.getId()).isEmpty());
    }
}