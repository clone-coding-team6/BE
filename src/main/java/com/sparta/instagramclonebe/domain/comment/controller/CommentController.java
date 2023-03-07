package com.sparta.instagramclonebe.domain.comment.controller;

import com.sparta.instagramclonebe.domain.comment.dto.CommentRequestDto;
import com.sparta.instagramclonebe.domain.comment.service.CommentService;
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/comment")
    public ResponseEntity<GlobalResponseDto> createComment(@RequestParam(value = "postId") Long postId,
                                                                               @RequestBody CommentRequestDto commentRequestDto,
                                                                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(postId, commentRequestDto, userDetails.getUser());
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<GlobalResponseDto> deleteComment(@PathVariable Long id,
                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }

    // 댓글 수정
//    @PutMapping("/commentNumber/{commentId}")
//    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
//                                                            @RequestBody CommentRequestDto commentRequestDto,
//                                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return commentService.updateComment(commentId, commentRequestDto, userDetails.getUser());
//    }

}
