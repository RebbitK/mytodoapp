package com.sparta.mytodoapp.entity;

import com.sparta.mytodoapp.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @Column(nullable = false)
    private String username;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name="schedule_id")
    private List<Comment> comments = new ArrayList<>();

    public Schedule(ScheduleRequestDto requestDto,User user) {
        this.title = requestDto.getTitle();
        this.text = requestDto.getText();
        this.username = user.getUsername();
    }

    public boolean getComplete() {
        return complete;
    }


    public void update(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.text= requestDto.getText();
    }
}
