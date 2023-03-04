package com.sparta.instagramclonebe.domain.like.service;

import com.sparta.instagramclonebe.domain.comment.entity.Comment;
import com.sparta.instagramclonebe.domain.comment.repository.CommentRepository;
import com.sparta.instagramclonebe.domain.like.entity.CommentLike;
import com.sparta.instagramclonebe.domain.like.entity.PostLike;
import com.sparta.instagramclonebe.domain.like.repository.CommentLikeRepository;
import com.sparta.instagramclonebe.domain.like.repository.PostLikeRepository;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.domain.post.repository.PostRepository;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.global.dto.StatusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    public ResponseEntity<StatusResponseDto> createPostLike(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );

        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUserId(id, user.getId());


        if (postLike.isEmpty()) {
            postLikeRepository.saveAndFlush(PostLike.of(post, user));
            return ResponseEntity.ok()
                    .body(new StatusResponseDto(HttpStatus.OK));
        } else {
            postLikeRepository.deleteByPostIdAndUserId(id, user.getId());
            return ResponseEntity.ok()
                    .body(new StatusResponseDto(HttpStatus.OK));
        }
    }

    public ResponseEntity<StatusResponseDto> createCommentsLike(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentIdAndUserId(id, user.getId());


        if (commentLike.isEmpty()) {
            commentLikeRepository.saveAndFlush(CommentLike.of(comment, user));
            return ResponseEntity.ok()
                    .body(new StatusResponseDto(HttpStatus.OK));
        } else {
            commentLikeRepository.deleteByCommentIdAndUserId(id, user.getId());
            return ResponseEntity.ok()
                    .body(new StatusResponseDto(HttpStatus.OK));
        }

    }
}
