package com.sparta.mytodoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mytodoapp.config.WebSecurityConfig;
import com.sparta.mytodoapp.dto.SignupRequestDto;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.jwt.JwtUtil;
import com.sparta.mytodoapp.mvc.MockSpringSecurityFilter;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import com.sparta.mytodoapp.service.UserServiceImpl;
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

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = UserController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
@ActiveProfiles("test")
class UserControllerTest {
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
	UserServiceImpl userServiceImpl;

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
    @DisplayName("회원가입 페이지 테스트")
    void signupPage() throws Exception {
        //given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("testuser");
        signupRequestDto.setPassword("password123");

        String requestBody = objectMapper.writeValueAsString(signupRequestDto);

        // when - then
        mvc.perform(post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자 정보 가져오기 테스트")
    void getUserInfo() throws Exception {
        //given
        this.testUser();
        // when - then
        mvc.perform(get("/api/user/info")
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal) // Authentication 객체 주입
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

}