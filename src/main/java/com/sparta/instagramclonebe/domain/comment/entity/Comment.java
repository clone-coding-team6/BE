package com.sparta.instagramclonebe.domain.comment.entity;


import com.sparta.instagramclonebe.domain.comment.dto.CommentRequestDto;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.global.util.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String contents;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @Builder
    private Comment(CommentRequestDto requestDto, User user, Post post){
        this.contents = requestDto.getContents();
        this.user = user;
        this.post = post;
    }

    public static Comment of(CommentRequestDto requestDto, User user, Post post){
        return Comment.builder()
                .requestDto(requestDto)
                .user(user)
                .post(post)
                .build();
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
