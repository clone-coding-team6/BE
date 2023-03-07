package com.sparta.instagramclonebe.domain.comment.service;

import com.sparta.instagramclonebe.domain.comment.dto.CommentRequestDto;
import com.sparta.instagramclonebe.domain.comment.dto.CommentResponseDto;
import com.sparta.instagramclonebe.domain.comment.entity.Comment;
import com.sparta.instagramclonebe.domain.comment.repository.CommentRepository;
import com.sparta.instagramclonebe.domain.like.repository.CommentLikeRepository;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.domain.post.repository.PostRepository;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.response.CustomStatusCode;
import com.sparta.instagramclonebe.global.response.exceptionType.CommentException;
import com.sparta.instagramclonebe.global.response.exceptionType.PostException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    // 댓글 작성
    @Transactional
    public ResponseEntity<GlobalResponseDto> createComment(Long id, CommentRequestDto requestDto, User user) {
        Post post = findPostByPostId(id);
        Comment comment = commentRepository.save(Comment.of(requestDto, user, post));
        return ResponseEntity.ok()
                .body(GlobalResponseDto.of(CustomStatusCode.COMMENT_UPLOAD_SUCCESS, CommentResponseDto.of(comment)));
    }
    // 댓글 삭제
    @Transactional
    public ResponseEntity<GlobalResponseDto> deleteComment(Long id, User user) {
        Comment comment = findCommentByCommentId(id);
        if(!comment.getUser().equals(user)){
            throw new CommentException(CustomStatusCode.COMMENT_DELETE_FAILED);
        }
        commentLikeRepository.deleteAllByCommentId(comment.getId());
        commentRepository.delete(comment);
        return ResponseEntity.ok()
                .body(GlobalResponseDto.of(CustomStatusCode.COMMENT_DELETE_SUCCESS));
    }

    private Comment findCommentByCommentId(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new CommentException(CustomStatusCode.COMMENT_NOT_FOUND)
        );
    }

    private Post findPostByPostId(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new PostException(CustomStatusCode.POST_NOT_FOUND)
        );
    }

    //    @Transactional
//    public ResponseEntity<CommentResponseDto> updateComment(Long id, CommentRequestDto requestDto, User user) {
//        Comment comment = findCommentByCommentId(id); //여기까진 create와 동일
//        // user가 관리자이거나 코멘트의 user와 토큰의 user가 일치하다면 update한다.
//        if(!comment.getUser().equals(user)){
//            throw new IllegalArgumentException("작성자만 댓글을 수정할 수 있습니다.");
//        }
//        comment.update(requestDto);
//        commentRepository.flush();
//        return ResponseEntity.ok()
//                .body(CommentResponseDto.of(comment));
//    }

}
