package com.sparta.instagramclonebe.domain.post.controller;

import com.sparta.instagramclonebe.domain.post.dto.PostRequestDto;
import com.sparta.instagramclonebe.domain.post.dto.PostResponseDto;
import com.sparta.instagramclonebe.domain.post.service.PostService;
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping("/posts")
    public ResponseEntity<GlobalResponseDto<PostResponseDto>> createReview(@RequestPart(value = "data") PostRequestDto postrequestDto,
                                                                           @RequestPart(value = "file")  List<MultipartFile> multipartFilelist,
                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.createPost(postrequestDto, userDetails.getUser(),multipartFilelist);
    }

    // 게시글 전체 조회
    @GetMapping("/posts")
    public ResponseEntity<GlobalResponseDto<List<PostResponseDto>>> getReviews() {
        return postService.getPosts();
    }

    // 게시글 상세 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<GlobalResponseDto<PostResponseDto>> getReview(@PathVariable Long id){
        return postService.getPost(id);
    }

    // 게시글 수정
    @PutMapping("/posts/{id}")
    public ResponseEntity<GlobalResponseDto<PostResponseDto>> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.update(id, requestDto, userDetails.getUser());
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<GlobalResponseDto<Void>> deleteReview(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }

    // 내가 쓴 리뷰 조회
//    @GetMapping("/users")
//    public ResponseEntity<List<PostDto>> getMyReviews(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return postService.getMyPosts(userDetails.getUser());
//    }

    // 내가 좋아요한 리뷰 조회
//    @GetMapping("/userlikes")
//    public ResponseEntity<List<PostDto>> getMyLikeReviews(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return postService.getMyLikeReviews(userDetails.getUser());
//    }

}

