package com.sparta.instagramclonebe.domain.comment.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class CommentRequestDto {
    @NotNull(message = "댓글 내용은 필수입니다.")
    @Size(min = 1, max = 300, message = "댓글은 1글자 이상 300글자 이하로만 작성할 수 있습니다.")
    private String content;
}
