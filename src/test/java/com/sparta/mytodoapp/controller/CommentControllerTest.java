package com.sparta.mytodoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mytodoapp.config.WebSecurityConfig;
import com.sparta.mytodoapp.dto.CommentRequestDto;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.jwt.JwtUtil;
import com.sparta.mytodoapp.mvc.MockSpringSecurityFilter;
import com.sparta.mytodoapp.security.UserDetailsImpl;
import com.sparta.mytodoapp.service.CommentService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = CommentController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
@ActiveProfiles("test")
class CommentControllerTest {

    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CommentService commentService;

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
    @DisplayName("댓글 작성 페이지 테스트")
    void createCommentPage() throws Exception {
        //given
        Long id = 10L;
        this.testUser();
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setComment("댓글");
        String requestBody = objectMapper.writeValueAsString(requestDto);
        //when-then
        mvc.perform(post("/api/comments/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 수정 페이지 테스트")
    void updateCommentPage() throws Exception {
        //given
        Long id = 10L;
        this.testUser();
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setComment("댓글수정");
        String requestBody = objectMapper.writeValueAsString(requestDto);
        //when-then
        mvc.perform(put("/api/comments/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
                .content(requestBody)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 삭제 페이지 테스트")
    void deleteCommentPage() throws Exception{
        //given
        Long id = 10L;
        this.testUser();
        //when-then
        mvc.perform(delete("/api/comments/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }
}