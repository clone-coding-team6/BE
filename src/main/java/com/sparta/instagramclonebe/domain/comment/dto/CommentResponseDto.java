package com.sparta.instagramclonebe.domain.comment.dto;

import com.sparta.instagramclonebe.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private String contents;
    private LocalDateTime createdAt;
    @Builder
    private CommentResponseDto(Comment comment){
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
    }
    public static CommentResponseDto of(Comment comment){
        return CommentResponseDto.builder()
                .comment(comment)
                .build();
    }
}
