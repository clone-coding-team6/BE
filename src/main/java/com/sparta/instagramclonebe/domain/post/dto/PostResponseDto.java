package com.sparta.instagramclonebe.domain.post.dto;

import com.sparta.instagramclonebe.domain.comment.dto.CommentResponseDto;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Long postId;
    private String content;
    private String imageUrl;
    private Long postLikeCount;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    @Builder
    private PostResponseDto(Post post, Long postLikeCount, List<CommentResponseDto> commentList){
        this.postId = post.getId();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.postLikeCount = postLikeCount;
        this.commentList = commentList;
    }

    public static PostResponseDto of(Post post){
        return PostResponseDto.builder()
                .post(post)
                .build();
    }

    public static PostResponseDto of(Post post, Long postLikeCount, List<CommentResponseDto> commentList){
        return PostResponseDto.builder()
                .post(post)
                .postLikeCount(postLikeCount)
                .commentList(commentList)
                .build();
    }
}
