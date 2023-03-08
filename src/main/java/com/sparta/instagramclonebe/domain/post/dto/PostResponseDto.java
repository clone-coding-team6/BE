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
    private String nickname;
    private String content;
    private Long postLikeCount;
    private List<CommentResponseDto> commentList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();
    private boolean isLiked;

    @Builder
    private PostResponseDto(Post post, Long postLikeCount, List<CommentResponseDto> commentList, List<String> imageList, boolean isLiked) {
        this.postId = post.getId();
        this.content = post.getContent();
        this.nickname = post.getUser().getNickname();
        this.postLikeCount = postLikeCount;
        this.commentList = commentList;
        this.isLiked = isLiked;
        this.imageList = imageList;
    }

    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .post(post)
                .build();
    }

    public static PostResponseDto of(Post post, List<String> imageList) {
        return PostResponseDto.builder()
                .post(post)
                .imageList(imageList)
                .build();
    }

    public static PostResponseDto of(Post post, Long postLikeCount, List<String> imageList, List<CommentResponseDto> commentList, boolean isLiked) {
        return PostResponseDto.builder()
                .post(post)
                .postLikeCount(postLikeCount)
                .commentList(commentList)
                .imageList(imageList)
                .isLiked(isLiked)
                .build();
    }
}
