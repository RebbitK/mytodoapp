package com.sparta.mytodoapp.entity;

import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "schedule")
@DynamicInsert
@DynamicUpdate
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private boolean complete = false;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name="schedule_id")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public Schedule(ScheduleRequestDto requestDto,User user) {
        this.title = requestDto.getTitle();
        this.text = requestDto.getText();
        this.user = user;
    }

    public boolean getComplete() {
        return complete;
    }


    public void update(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.text= requestDto.getText();
    }
}
