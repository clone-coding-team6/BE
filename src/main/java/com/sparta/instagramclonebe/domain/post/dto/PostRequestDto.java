package com.sparta.instagramclonebe.domain.post.dto;


import com.sparta.instagramclonebe.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    @Size(min = 1, max = 3000, message = "게시글 내용은 1글자 이상 3000자 이하로만 작성할 수 있습니다.")
    private String content;

    @Builder
    private PostRequestDto(Post post){
        this.content = post.getContent();
    }

    public static PostRequestDto of(Post post){
        return PostRequestDto.builder()
                .post(post)
                .build();
    }
}
