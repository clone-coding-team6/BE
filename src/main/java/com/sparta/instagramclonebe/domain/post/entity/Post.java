package com.sparta.instagramclonebe.domain.post.entity;

import com.sparta.instagramclonebe.domain.post.dto.PostRequestDto;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.global.util.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Builder
    private Post(PostRequestDto postDto, User user) {
        this.content = postDto.getContent();
        this.imageUrl = postDto.getImageUrl();
        this.user = user;
    }

    public static Post of(PostRequestDto postDto, User user) {
        return Post.builder()
                .postDto(postDto)
                .user(user)
                .build();
    }

    public void update(PostRequestDto postDto) {
        this.content = postDto.getContent();
    }

}
