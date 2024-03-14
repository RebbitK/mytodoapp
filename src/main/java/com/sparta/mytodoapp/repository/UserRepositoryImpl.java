package com.sparta.mytodoapp.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mytodoapp.entity.QUserEntity;
import com.sparta.mytodoapp.entity.UserEntity;
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
	public Optional<UserEntity> findByUsername(String username) {
		UserEntity query = jpaQueryFactory.select(QUserEntity.userEntity)
			.from(QUserEntity.userEntity)
			.where(
				usernameEq(username)
			)
			.fetchOne();
		return Optional.ofNullable(query);
	}

	@Override
	public Optional<LoginInfo> findByUsernameAndPassword(String username) {
		LoginInfo query = jpaQueryFactory.select(
				Projections.fields(LoginInfo.class, QUserEntity.userEntity.id, QUserEntity.userEntity.username,QUserEntity.userEntity.password))
			.from(QUserEntity.userEntity)
			.where(
				usernameEq(username)
			)
			.fetchOne();
		return Optional.ofNullable(query);
	}

	private BooleanExpression usernameEq(String username) {
		return Objects.nonNull(username) ? QUserEntity.userEntity.username.eq(username) : null;
	}

	private BooleanExpression passwordEq(String password) {
		return Objects.nonNull(password) ? QUserEntity.userEntity.password.eq(password) : null;
	}

}
