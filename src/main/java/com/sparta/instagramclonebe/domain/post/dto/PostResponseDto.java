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
    private Long postLikeCount;
    private List<CommentResponseDto> commentList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();

    @Builder
    private PostResponseDto(Post post, Long postLikeCount, List<CommentResponseDto> commentList, List<String> imageList){
        this.postId = post.getId();
        this.content = post.getContent();
        this.postLikeCount = postLikeCount;
        this.commentList = commentList;
        this.imageList = imageList;
    }
    public static PostResponseDto of(Post post){
        return PostResponseDto.builder()
                .post(post)
                .build();
    }
    public static PostResponseDto of(Post post,List<String> imageList){
        return PostResponseDto.builder()
                .post(post)
                .imageList(imageList)
                .build();
    }

    public static PostResponseDto of(Post post, Long postLikeCount, List<CommentResponseDto> commentList, List<String> imageList){
        return PostResponseDto.builder()
                .post(post)
                .postLikeCount(postLikeCount)
                .commentList(commentList)
                .imageList(imageList)
                .build();
    }
}
