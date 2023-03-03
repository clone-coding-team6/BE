package com.sparta.instagramclonebe.domain.post.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    private String content;
    private String imageUrl;
}
