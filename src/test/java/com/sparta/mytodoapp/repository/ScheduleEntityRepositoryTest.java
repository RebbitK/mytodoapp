package com.sparta.mytodoapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sparta.mytodoapp.config.TestConfig;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import com.sparta.mytodoapp.entity.UserEntity;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class ScheduleEntityRepositoryTest {


    @Autowired
    private JpaScheduleRepository scheduleRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    private Pageable pageable = PageRequest.of(0, 5);

    private ScheduleEntity testSchedule(){
        ScheduleRequestDto requestDto = new ScheduleRequestDto("제목", "내용");
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        UserEntity userEntity = new UserEntity(username,password,role);
        return new ScheduleEntity(requestDto, userEntity);
    }

    @Test
    @DisplayName("할일카드 유저 이름 검색 테스트")
    void findByUsername() {
        //given
        UserEntity userEntity = new UserEntity("Test","12345678",UserRoleEnum.USER);
        ScheduleEntity scheduleEntity1 = new ScheduleEntity(new ScheduleRequestDto("제목1","내용1"),
            userEntity);
        ScheduleEntity scheduleEntity2 = new ScheduleEntity(new ScheduleRequestDto("제목2","내용2"),
            userEntity);
        jpaUserRepository.save(userEntity);
        scheduleRepository.save(scheduleEntity1);
        scheduleRepository.save(scheduleEntity2);
        //when
        Optional<Page<ScheduleEntity>> testSchedule = scheduleRepository.findByUsername(userEntity.getUsername(),pageable);
        //then
        assertEquals(scheduleEntity1.getTitle(),testSchedule.get().getContent().get(0).getTitle());
        assertEquals(scheduleEntity2.getTitle(),testSchedule.get().getContent().get(1).getTitle());
    }

    @Test
    @DisplayName("할일카드 작성된 순서 내림차순 검색 테스트")
    void findAllByOrderByModifiedAtDesc() {
        //given
        UserEntity userEntity = new UserEntity("Test","12345678",UserRoleEnum.USER);
        ScheduleEntity scheduleEntity1 = new ScheduleEntity(new ScheduleRequestDto("제목1","내용1"),
            userEntity);
        ScheduleEntity scheduleEntity2 = new ScheduleEntity(new ScheduleRequestDto("제목2","내용2"),
            userEntity);
        List<ScheduleEntity> scheduleEntities = new ArrayList<>();
        scheduleEntities.add(scheduleEntity1);
        scheduleEntities.add(scheduleEntity2);
        jpaUserRepository.save(userEntity);
        scheduleRepository.save(scheduleEntity1);
        scheduleRepository.save(scheduleEntity2);
        //when
        Optional<Page<ScheduleEntity>> testSchedule = scheduleRepository.findAllByOrderByModifiedAtDesc(pageable);
        //then
        assertEquals(scheduleEntity1.getTitle(),testSchedule.get().getContent().get(1).getTitle());
        assertEquals(scheduleEntity2.getTitle(),testSchedule.get().getContent().get(0).getTitle());
    }

}