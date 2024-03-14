package com.sparta.mytodoapp.entity;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@DynamicInsert
@DynamicUpdate
public class CommentEntity extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private String username;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private ScheduleEntity scheduleEntity;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

    public CommentEntity(CommentRequestDto requestDto, UserEntity userEntity,ScheduleEntity scheduleEntity) {
        this.comment = requestDto.getComment();
        this.username = userEntity.getUsername();
        this.userEntity = userEntity;
        this.scheduleEntity = scheduleEntity;
    }

}
