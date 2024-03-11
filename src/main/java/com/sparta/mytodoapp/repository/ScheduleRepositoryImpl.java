package com.sparta.mytodoapp.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mytodoapp.entity.QSchedule;
import com.sparta.mytodoapp.entity.Schedule;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;


@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<Page<Schedule>> findByUsername(String username, Pageable pageable) {
		var query = jpaQueryFactory.select(QSchedule.schedule)
			.from(QSchedule.schedule)
			.where(
				usernameEq(username)
			)
			.leftJoin(QSchedule.schedule.comments).fetchJoin()
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		var count = jpaQueryFactory.select(Wildcard.count)
			.from(QSchedule.schedule)
			.where(
				usernameEq(username)
			)
			.fetch()
			.get(0);

		return Optional.of(PageableExecutionUtils.getPage(query, pageable, () -> count));
	}

	@Override
	public Optional<Page<Schedule>> findAllByOrderByModifiedAtDesc(Pageable pageable) {
		var query = jpaQueryFactory.select(QSchedule.schedule)
			.from(QSchedule.schedule)
			.leftJoin(QSchedule.schedule.comments).fetchJoin()
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(QSchedule.schedule.modifiedAt.desc())
			.fetch();
		var count = jpaQueryFactory.select(Wildcard.count)
			.from(QSchedule.schedule)
			.fetch()
			.get(0);

		return Optional.of(PageableExecutionUtils.getPage(query, pageable, () -> count));
	}

	private BooleanExpression usernameEq(String username) {
		return Objects.nonNull(username) ? QSchedule.schedule.user.username.eq(username) : null;
	}


}