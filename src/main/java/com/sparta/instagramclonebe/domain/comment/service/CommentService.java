package com.sparta.instagramclonebe.domain.comment.service;

import com.sparta.instagramclonebe.domain.comment.dto.CommentRequestDto;
import com.sparta.instagramclonebe.domain.comment.dto.CommentResponseDto;
import com.sparta.instagramclonebe.domain.comment.entity.Comment;
import com.sparta.instagramclonebe.domain.comment.repository.CommentRepository;
import com.sparta.instagramclonebe.domain.like.repository.CommentLikeRepository;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.domain.post.repository.PostRepository;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.global.util.ResponseUtils;
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.excpetion.ErrorCode;
import com.sparta.instagramclonebe.global.excpetion.exceptionType.CommentException;
import com.sparta.instagramclonebe.global.excpetion.exceptionType.PostException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public ResponseEntity<GlobalResponseDto<CommentResponseDto>> createComment(Long id, CommentRequestDto requestDto, User user) {
        Post post = findPostByPostId(id); // pathvariable로 받아 왔던 게시글의 id를 통해 게시글 찾는다
        Comment comment = commentRepository.save(Comment.of(requestDto, user, post)); // 코멘트를 생성하고 저장한다.
        return new ResponseEntity<>(ResponseUtils.ok(CommentResponseDto.of(comment)), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<GlobalResponseDto<Void>> deleteComment(Long id, User user) {
        Comment comment = findCommentByCommentId(id);
        if(!comment.getUser().equals(user)){
            throw new CommentException(ErrorCode.COMMENT_DELETE_FAILED);
        }
        commentLikeRepository.deleteAllByCommentId(comment.getId());
        commentRepository.delete(comment);
        return new ResponseEntity<>(ResponseUtils.ok(null), HttpStatus.OK);
    }
    private Comment findCommentByCommentId(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new CommentException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }

    private Post findPostByPostId(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new PostException(ErrorCode.POST_NOT_FOUND)
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
