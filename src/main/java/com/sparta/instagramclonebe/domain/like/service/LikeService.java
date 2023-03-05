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
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.excpetion.ErrorCode;
import com.sparta.instagramclonebe.global.excpetion.exceptionType.PostException;
import com.sparta.instagramclonebe.global.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<GlobalResponseDto<String>> createPostLike(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostException(ErrorCode.POST_NOT_FOUND)
        );

        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUserId(id, user.getId());


        if (postLike.isEmpty()) {
            postLikeRepository.saveAndFlush(PostLike.of(post, user));
            return new ResponseEntity<>(ResponseUtils.ok("좋아요 성공!"), HttpStatus.OK);

        } else {
            postLikeRepository.deleteByPostIdAndUserId(id, user.getId());
            return new ResponseEntity<>(ResponseUtils.ok("좋아요 취소!"), HttpStatus.OK);

        }
    }

    @Transactional
    public ResponseEntity<GlobalResponseDto<String>> createCommentsLike(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new PostException(ErrorCode.COMMENT_NOT_FOUND)
        );

        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentIdAndUserId(id, user.getId());


        if (commentLike.isEmpty()) {
            commentLikeRepository.saveAndFlush(CommentLike.of(comment, user));
            return new ResponseEntity<>(ResponseUtils.ok("좋아요 성공!"), HttpStatus.OK);
        } else {
            commentLikeRepository.deleteByCommentIdAndUserId(id, user.getId());
            return new ResponseEntity<>(ResponseUtils.ok("좋아요 취소!"), HttpStatus.OK);
        }

    }
}
