package com.sparta.mytodoapp.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mytodoapp.entity.QUser;
import com.sparta.mytodoapp.entity.User;
import com.sparta.mytodoapp.projection.LoginInfo;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<User> findByUsername(String username) {
		User query = jpaQueryFactory.select(QUser.user)
			.from(QUser.user)
			.where(
				usernameEq(username)
			)
			.fetchOne();
		return Optional.ofNullable(query);
	}

	@Override
	public Optional<LoginInfo> findByUsernameAndPassword(String username) {
		LoginInfo query = jpaQueryFactory.select(
				Projections.fields(LoginInfo.class, QUser.user.id, QUser.user.username,QUser.user.password))
			.from(QUser.user)
			.where(
				usernameEq(username)
			)
			.fetchOne();
		return Optional.ofNullable(query);
	}

	private BooleanExpression usernameEq(String username) {
		return Objects.nonNull(username) ? QUser.user.username.eq(username) : null;
	}

	private BooleanExpression passwordEq(String password) {
		return Objects.nonNull(password) ? QUser.user.password.eq(password) : null;
	}

}
