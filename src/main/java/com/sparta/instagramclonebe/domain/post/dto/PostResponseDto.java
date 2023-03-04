package com.sparta.instagramclonebe.domain.post.dto;

import com.sparta.instagramclonebe.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Long postId;
    private String content;
    private String imageUrl;
    private int postLikeCount;

    @Builder
    private PostResponseDto(Post post,int postLikeCount){
        this.postId = post.getId();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.postLikeCount = postLikeCount;
    }

    public static PostResponseDto of(Post post){
        return PostResponseDto.builder()
                .post(post)
                .build();
    }
    public static PostResponseDto of(Post post, int postLikeCount){
        return PostResponseDto.builder()
                .post(post)
                .postLikeCount(postLikeCount)
                .build();
    }
}
