package com.sparta.mytodoapp;



import com.sparta.mytodoapp.entity.Schedule;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.repository.ScheduleRepository;
import com.sparta.mytodoapp.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class OneToManyTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ScheduleRepository foodRepository;


    @Test
    @Rollback(value = false)
    @DisplayName("1대N 단방향 테스트")
    void test1() {
        User user = new User();
        user.setUsername("kk");
        user.setPassword("aa");
        user.setRole(UserRoleEnum.USER);

        Schedule schedule = new Schedule();
        schedule.setTitle("테스트");
        schedule.setText("xptmxm");
        schedule.setUsername("kk");
        user.getSchedules().add(schedule);

        foodRepository.save(schedule);
        userRepository.save(user);

        // 추가적인 UPDATE 쿼리 발생을 확인할 수 있습니다.
    }
}