package com.sparta.instagramclonebe.domain.comment.service;

import com.sparta.instagramclonebe.domain.comment.dto.CommentRequestDto;
import com.sparta.instagramclonebe.domain.comment.dto.CommentResponseDto;
import com.sparta.instagramclonebe.domain.comment.entity.Comment;
import com.sparta.instagramclonebe.domain.comment.repository.CommentRepository;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.domain.post.repository.PostRepository;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.global.dto.StatusResponseDto;
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

    @Transactional
    public ResponseEntity<CommentResponseDto> createComment(Long id, CommentRequestDto requestDto, User user) {
        Post post = findPostByPostId(id); // pathvariable로 받아 왔던 게시글의 id를 통해 게시글 찾는다
        Comment comment = commentRepository.save(Comment.of(requestDto, user, post)); // 코멘트를 생성하고 저장한다.
        return ResponseEntity.ok()
                .body(CommentResponseDto.of(comment));
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

    @Transactional
    public ResponseEntity<StatusResponseDto> deleteComment(Long id, User user) {
        Comment comment = findCommentByCommentId(id);
        if(!comment.getUser().equals(user)){
            throw new IllegalArgumentException("작성자만 댓글을 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
        return ResponseEntity.ok()
                .body(new StatusResponseDto(HttpStatus.OK));
    }
    private Comment findCommentByCommentId(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );
    }

    private Post findPostByPostId(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );
    }
}
