package com.sparta.instagramclonebe.domain.like.controller;

import com.sparta.instagramclonebe.domain.like.service.LikeService;
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    // 게시글 좋아요
    @PostMapping("/posts/likes/{id}")
    public ResponseEntity<GlobalResponseDto> createPostLike(@PathVariable Long id,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.createPostLike(id, userDetails.getUser());
    }

    // 댓글 좋아요
    @PostMapping("/comment/likes/{id}")
    public ResponseEntity<GlobalResponseDto> createCommentsLike(@PathVariable Long id,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.createCommentsLike(id, userDetails.getUser());
    }
}
