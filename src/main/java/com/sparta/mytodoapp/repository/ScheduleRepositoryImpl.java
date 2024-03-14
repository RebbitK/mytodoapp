package com.sparta.mytodoapp.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mytodoapp.entity.QCommentEntity;
import com.sparta.mytodoapp.entity.QScheduleEntity;
import com.sparta.mytodoapp.entity.ScheduleEntity;
import com.sparta.mytodoapp.model.Schedule;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {

	private final JPAQueryFactory jpaQueryFactory;

	private final EntityManager entityManager;

	@Override
	public Optional<Page<ScheduleEntity>> findByUsername(String username, Pageable pageable) {
		List<ScheduleEntity> query = jpaQueryFactory.select(QScheduleEntity.scheduleEntity)
			.from(QScheduleEntity.scheduleEntity)
			.where(
				usernameEq(username)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		Long count = jpaQueryFactory.select(Wildcard.count)
			.from(QScheduleEntity.scheduleEntity)
			.where(
				usernameEq(username)
			)
			.fetch()
			.get(0);

		return Optional.of(PageableExecutionUtils.getPage(query, pageable, () -> count));
	}

	@Override
	public Optional<Page<ScheduleEntity>> findAllByOrderByModifiedAtDesc(Pageable pageable) {
		List<ScheduleEntity> query = jpaQueryFactory.select(QScheduleEntity.scheduleEntity)
			.from(QScheduleEntity.scheduleEntity)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(QScheduleEntity.scheduleEntity.modifiedAt.desc())
			.fetch();
		Long count = jpaQueryFactory.select(Wildcard.count)
			.from(QScheduleEntity.scheduleEntity)
			.fetch()
			.get(0);

		return Optional.of(PageableExecutionUtils.getPage(query, pageable, () -> count));
	}

	@Override
	public Optional<ScheduleEntity> findByIdGet(Long id){
		ScheduleEntity query = jpaQueryFactory.select(QScheduleEntity.scheduleEntity)
			.from(QScheduleEntity.scheduleEntity)
			.where(
				userIdEq(id)
			)
			.leftJoin(QCommentEntity.commentEntity).on(QScheduleEntity.scheduleEntity.id.eq(QCommentEntity.commentEntity.scheduleEntity.id))
			.fetch()
			.get(0);
		return Optional.of(query);
	}

	@Override
	public ScheduleEntity update(ScheduleEntity scheduleEntity){
		return entityManager.merge(scheduleEntity);
	}

	@Override
	public void deleteSchedule(ScheduleEntity scheduleEntity){
		entityManager.remove(scheduleEntity);
	}

	private BooleanExpression usernameEq(String username) {
		return Objects.nonNull(username) ? QScheduleEntity.scheduleEntity.username.eq(username) : null;
	}

	private BooleanExpression userIdEq(Long id){
		return Objects.nonNull(id) ? QScheduleEntity.scheduleEntity.id.eq(id) : null;
	}


}
