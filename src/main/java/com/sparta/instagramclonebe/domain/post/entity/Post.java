package com.sparta.instagramclonebe.domain.post.entity;

import com.sparta.instagramclonebe.domain.image.entity.Image;
import com.sparta.instagramclonebe.domain.post.dto.PostRequestDto;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.global.util.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    @Builder
    private Post(PostRequestDto postRequestDto, User user) {
        this.content = postRequestDto.getContent();
        this.user = user;
    }

    public static Post of(PostRequestDto postRequestDtoDto, User user) {
        return Post.builder()
                .postRequestDto(postRequestDtoDto)
                .user(user)
                .build();
    }

    public void update(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
    }

}
