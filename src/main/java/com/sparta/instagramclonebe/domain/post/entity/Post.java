package com.sparta.instagramclonebe.domain.post.entity;

import com.sparta.instagramclonebe.domain.post.dto.PostRequestDto;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.global.util.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
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

    public static Post of(PostRequestDto postRequestDto, User user) {
        return Post.builder()
                .postRequestDto(postRequestDto)
                .user(user)
                .build();
    }

    public void update(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
    }

}
