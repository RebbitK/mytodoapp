package com.sparta.mytodoapp;



import com.sparta.mytodoapp.repository.ScheduleRepository;
import com.sparta.mytodoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class OneToManyTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ScheduleRepository foodRepository;


}