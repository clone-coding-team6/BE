package com.sparta.instagramclonebe.domain.comment.entity;


import com.sparta.instagramclonebe.domain.comment.dto.CommentRequestDto;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.global.util.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;


    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @Builder
    private Comment(CommentRequestDto requestDto, User user, Post post){
        this.content = requestDto.getContent();
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
        this.content = requestDto.getContent();
    }
}
