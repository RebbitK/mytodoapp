package com.sparta.mytodoapp.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mytodoapp.config.WebSecurityConfig;
import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.jwt.JwtUtil;
import com.sparta.mytodoapp.mvc.MockSpringSecurityFilter;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import com.sparta.mytodoapp.service.ScheduleServiceImpl;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = ScheduleController.class,
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
                )
        }
)
@ActiveProfiles("test")
public class ScheduleControllerTest {
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ScheduleServiceImpl scheduleServiceImpl;

    @MockBean
    JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void testUser() {
        String username = "Test";
        String password = "12345678";
        UserRoleEnum role = UserRoleEnum.USER;
        User user = new User(username, password, role);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(user);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }


    @Test
    @DisplayName("할일 카드 만들기 페이지 테스트")
    void createSchedulePage() throws Exception {
        //given
        this.testUser();
        ScheduleRequestDto requestDto = new ScheduleRequestDto("제목", "내용");
        String requestBody = objectMapper.writeValueAsString(requestDto);
        //when-then
        mvc.perform(post("/api/schedules")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("할일 카드 전체 조회 페이지 테스트")
    void getSchedulesPage() throws Exception {
        //given
        //when-then
        mvc.perform(get("/api/schedules")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("나의 할일 카드 조회 페이지 테스트")
    void getMySchedulesPage() throws  Exception {
        //given
        this.testUser();
        //when-then
        mvc.perform(get("/api/my-schedules")
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선택 할일 카드 조회 페이지 테스트")
    void getSchedulePage() throws Exception{
        //given
        Long id = 10L;
        //when-then
        mvc.perform(get("/api/schedules/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("할일 카드 수정 페이지 테스트")
    void updateSchedulePage() throws Exception{
        //given
        Long id = 10L;
        this.testUser();
        ScheduleRequestDto requestDto = new ScheduleRequestDto("제목수정", "내용수정");
        String requestBody = objectMapper.writeValueAsString(requestDto);
        //then-when
        mvc.perform(put("/api/schedules/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("할일 카드 삭제 페이지 테스트")
    void deleteSchedulePage() throws Exception{
        //given
        Long id = 10L;
        this.testUser();
        //then-when
        mvc.perform(delete("/api/schedules/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("할일 카드 완료 페이지 테스트")
    void completeSchedulePage() throws Exception{
        //given
        Long id = 10L;
        this.testUser();
        //then-when
        mvc.perform(get("/api/schedules-complete/{id}",id)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

}
