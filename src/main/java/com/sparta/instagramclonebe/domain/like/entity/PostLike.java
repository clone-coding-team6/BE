package com.sparta.instagramclonebe.domain.like.entity;

import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "postLike")
@NoArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POST_ID",nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "USER_ID",nullable = false)
    private User user;

    @Builder
    private PostLike(Post post, User user)
    {
        this.post =post;
        this.user = user;
    }

    public static PostLike of(Post post, User user)
    {
        return PostLike.builder()
                .post(post)
                .user(user)
                .build();
    }
}

