package com.sparta.mytodoapp.security;

import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.entity.UserRoleEnum;
import com.sparta.mytodoapp.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
		FilterChain filterChain) throws ServletException, IOException {

		String tokenValue = jwtUtil.getJwtFromHeader(req);

		//if (StringUtils.hasText(tokenValue)) {
			try {
				Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
				Long userId = info.get("userId",Long.class);
				String username = info.getSubject();
					setAuthentication(userId,username);
			} catch (Exception e) {
				log.error(e.getMessage());
				return;
			}
		//}

		filterChain.doFilter(req, res);
	}

	// 인증 처리
	public void setAuthentication(Long userId, String username) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(userId, username);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	// 인증 객체 생성
	private Authentication createAuthentication(Long userId, String username) {
		User user = new User(userId, username);
		user.setRole(UserRoleEnum.USER);
		UserDetails userDetails = new UserDetailsImpl(user);
		return new MyCustomAuthentication(userDetails);
	}
}