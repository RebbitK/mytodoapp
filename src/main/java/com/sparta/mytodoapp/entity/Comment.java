package com.sparta.mytodoapp.entity;

import com.sparta.mytodoapp.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
@DynamicInsert
@DynamicUpdate
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private String username;

    public Comment(CommentRequestDto requestDto, User user) {
        this.comment = requestDto.getComment();
        this.username = user.getUsername();
    }
    public Comment(String comment,User user){
        this.comment = comment;
        this.username = user.getUsername();
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
