package com.sparta.instagramclonebe.domain.like.entity;

import com.sparta.instagramclonebe.domain.comment.entity.Comment;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID",nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "USER_ID",nullable = false)
    private User user;


    @Builder
    private CommentLike(Comment comment, User user)
    {
        this.comment =comment;
        this.user = user;
    }

    public static CommentLike of(Comment comment, User user)
    {
        return CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();
    }
}
