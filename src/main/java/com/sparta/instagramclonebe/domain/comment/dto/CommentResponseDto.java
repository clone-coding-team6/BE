package com.sparta.instagramclonebe.domain.comment.dto;

import com.sparta.instagramclonebe.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long commentLikeCount;

    @Builder
    private CommentResponseDto(Comment comment, Long commentLikeCount){
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.id = comment.getId();
        this.commentLikeCount = commentLikeCount;
    }

    public static CommentResponseDto of(Comment comment){
        return CommentResponseDto.builder()
                .comment(comment)
                .commentLikeCount(0L)
                .build();
    }

    public static CommentResponseDto of(Comment comment, Long commentLikeCount){
        return CommentResponseDto.builder()
                .comment(comment)
                .commentLikeCount(commentLikeCount)
                .build();
    }
}
