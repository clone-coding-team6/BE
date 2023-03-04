package com.sparta.instagramclonebe.domain.comment.controller;

import com.sparta.instagramclonebe.domain.comment.dto.CommentRequestDto;
import com.sparta.instagramclonebe.domain.comment.dto.CommentResponseDto;
import com.sparta.instagramclonebe.domain.comment.service.CommentService;
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comment")
public class CommentController {

    private final CommentService commentService;

    //create
    @PostMapping("/")
    public ResponseEntity<GlobalResponseDto<CommentResponseDto>> createComment(@RequestParam(value = "postId") Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(postId, commentRequestDto, userDetails.getUser());
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponseDto<Void>> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }

    //    //update
//    @PutMapping("/commentnumber/{commentId}")
//    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return commentService.updateComment(commentId, commentRequestDto, userDetails.getUser());
//    }
}
