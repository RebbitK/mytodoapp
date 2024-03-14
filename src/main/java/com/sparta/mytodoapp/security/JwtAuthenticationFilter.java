package com.sparta.mytodoapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sparta.mytodoapp.dto.LoginRequestDto;
import com.sparta.mytodoapp.entity.UserEntity;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.jwt.JwtUtil;
import com.sparta.mytodoapp.repository.JpaUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtUtil jwtUtil;

	private final JpaUserRepository jpaUserRepository;

	private final PasswordEncoder passwordEncoder;



	public JwtAuthenticationFilter(JwtUtil jwtUtil, JpaUserRepository jpaUserRepository,
		PasswordEncoder passwordEncoder) {
		this.jwtUtil = jwtUtil;
		this.jpaUserRepository = jpaUserRepository;
		this.passwordEncoder = passwordEncoder;
		setFilterProcessesUrl("/api/user/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
		HttpServletResponse response) throws AuthenticationException {
		try {
			LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
				LoginRequestDto.class);
			//인증 처리를 하는 메서드 입력받은 아이디와 비밀번호로 검증
			UserEntity userEntity = jpaUserRepository.findByUsername(
					requestDto.getUsername())
				.orElseThrow(
					() -> new BadCredentialsException("잘못된 아이디를 입력하셨습니다.")); //인증실패를 위한 예외
			if(!passwordEncoder.matches(requestDto.getPassword(), userEntity.getPassword()))
			{
				throw new BadCredentialsException("잘못된 비밀번호를 입력하셨습니다.");
			}
			return new CustomAuthenticationToken(
				userEntity,
				requestDto.getPassword()
			);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
		HttpServletResponse response, FilterChain chain, Authentication authResult)
		throws IOException {
		UserEntity userEntity = (UserEntity) authResult.getPrincipal();
		String token = jwtUtil.createToken(userEntity.getId(), userEntity.getUsername(),
			UserRoleEnum.USER);
		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
		ObjectNode json = new ObjectMapper().createObjectNode();
		json.put("message", "상태코드:200 로그인성공                     ");
		String newResponse = new ObjectMapper().writeValueAsString(json);
		response.setContentType("application/json");
		response.setContentLength(newResponse.length());
		response.getOutputStream().write(newResponse.getBytes());
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
		HttpServletResponse response, AuthenticationException failed) throws IOException {
		response.setStatus(401);
		ObjectNode json = new ObjectMapper().createObjectNode();
		json.put("message", failed.getMessage()+"                                ");
		String newResponse = new ObjectMapper().writeValueAsString(json);
		response.setContentType("application/json");
		response.setContentLength(newResponse.length());
		response.getOutputStream().write(newResponse.getBytes());
		log.error(failed.getMessage());
	}

}