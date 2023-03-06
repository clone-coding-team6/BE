package com.sparta.instagramclonebe.domain.post.service;


import com.sparta.instagramclonebe.domain.comment.dto.CommentResponseDto;
import com.sparta.instagramclonebe.domain.comment.entity.Comment;
import com.sparta.instagramclonebe.domain.comment.repository.CommentRepository;
import com.sparta.instagramclonebe.domain.image.entity.Image;
import com.sparta.instagramclonebe.domain.image.repository.ImageRepository;
import com.sparta.instagramclonebe.domain.like.repository.CommentLikeRepository;
import com.sparta.instagramclonebe.domain.like.repository.PostLikeRepository;
import com.sparta.instagramclonebe.domain.post.dto.PostRequestDto;
import com.sparta.instagramclonebe.domain.post.dto.PostResponseDto;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.domain.post.repository.PostRepository;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.excpetion.ErrorCode;
import com.sparta.instagramclonebe.global.excpetion.exceptionType.PostException;
import com.sparta.instagramclonebe.global.util.ResponseUtils;
import com.sparta.instagramclonebe.global.util.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    // 게시글 작성
    @Transactional
    public ResponseEntity<GlobalResponseDto<PostResponseDto>> createPost(PostRequestDto postRequestDto, User user, List<MultipartFile> multipartFilelist) throws IOException {

        Post post = postRepository.saveAndFlush(Post.of(postRequestDto, user));

        if (multipartFilelist != null) {
            s3Service.upload(multipartFilelist, "static", post, user);
        }
        return new ResponseEntity<>(ResponseUtils.ok(PostResponseDto.of(post)), HttpStatus.OK);

    }

    // 전체 게시글 조회
    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto<List<PostResponseDto>>> getPosts() {

        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        for (Post post : postList) {
            List<String> imagePathList = ImagePathList(post);
            List<CommentResponseDto> commentResponseDtoList = getCommentResponseDtoList(post);
            responseDtoList.add(PostResponseDto.of(post, postLikeRepository.countPostLikeByPostId(post.getId()), commentResponseDtoList, imagePathList));
        }
        return new ResponseEntity<>(ResponseUtils.ok(responseDtoList), HttpStatus.OK);
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto<PostResponseDto>> getPost(Long id) {

        Post post = getPostById(id);
        List<String> imagePathList = ImagePathList(post);
        List<CommentResponseDto> commentResponseDtoList = getCommentResponseDtoList(post);
        return new ResponseEntity<>(ResponseUtils.ok(PostResponseDto
                .of(post, postLikeRepository.countPostLikeByPostId(post.getId()), commentResponseDtoList,imagePathList)), HttpStatus.OK);
    }

    // 게시글 수정
    @Transactional
    public ResponseEntity<GlobalResponseDto<PostResponseDto>> update(Long id, PostRequestDto requestDto, User user) {

        Post post = getPostById(id);
        if (!post.getUser().equals(user)) {
            throw new PostException(ErrorCode.POST_UPDATE_FAILED);
        }
        post.update(requestDto);
        postRepository.flush();
        List<String> imagePathList = ImagePathList(post);
        List<CommentResponseDto> commentResponseDtoList = getCommentResponseDtoList(post);
        return new ResponseEntity<>(ResponseUtils.ok(PostResponseDto
                .of(post, postLikeRepository.countPostLikeByPostId(post.getId()), commentResponseDtoList,imagePathList)), HttpStatus.OK);
    }

    // 게시글 삭제
    @Transactional
    public ResponseEntity<GlobalResponseDto<Void>> deletePost(Long id, User user) {

        Post post = getPostById(id);
        if (!post.getUser().equals(user)) {
            throw new PostException(ErrorCode.POST_DELETE_FAILED);
        }

        List<Comment> commentList = commentRepository.findAllByPostId(post.getId());
        for (Comment comment : commentList) {
            commentLikeRepository.deleteAllByCommentId(comment.getId());
        }
        List<Image> imagePathList = imageRepository.findAllByPostId(post.getId());
        for (Image image : imagePathList) {
            String uploadPath = image.getUploadPath();
            String filename = uploadPath.substring(61);
            s3Service.deleteFile(filename);
        }

        imageRepository.deleteAllByPostId(post.getId());
        postLikeRepository.deleteAllByPostId(post.getId());
        commentRepository.deleteAllByPostId(post.getId());
        postRepository.deleteById(post.getId());
        return new ResponseEntity<>(ResponseUtils.ok(null), HttpStatus.OK);
    }

    /* ========================================= METHOD =========================================*/

    private List<CommentResponseDto> getCommentResponseDtoList(Post post) {
        List<Comment> commentList = commentRepository.findAllByPostId(post.getId());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            Long commentLikeCount = commentLikeRepository.countByComment(comment);
            commentResponseDtoList.add(CommentResponseDto.of(comment, commentLikeCount));
        }
        return commentResponseDtoList;
    }

    private List<String> ImagePathList (Post post) {
        List<Image> imageList = imageRepository.findAllByPostId(post.getId());
        List<String> imagePathList = new ArrayList<>();

        for (Image image : imageList) {
            imagePathList.add(image.getUploadPath());
        }
        return imagePathList;
    }

    private Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new PostException(ErrorCode.POST_NOT_FOUND)
        );
    }

//    // 내가 쓴 리뷰 조회
//    @Transactional(readOnly = true)
//    public SuccessResponseDto<List<ReviewResponseDto>> getMyReviews(int pageNo, String criteria, User user) {
//
//        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, criteria));
//        Page<ReviewResponseDto> page = postRepository.findAllByUser(user, pageable).map(ReviewResponseDto::from);
//
//        checkInvalidPage(pageNo, page.getTotalElements());
//
//        return ResponseUtils.ok(page.getContent());
//    }
//
//    // 내가 좋아요한 리뷰 조회
//    @Transactional(readOnly = true)
//    public SuccessResponseDto<List<ReviewResponseDto>> getMyLikeReviews(int pageNo, String criteria, User user) {
//
//        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, criteria));
//        Page<ReviewResponseDto> page = postRepository.findAllByLikeReviewListUser(user, pageable).map(ReviewResponseDto::from);
//
//        return ResponseUtils.ok(page.getContent());
//    }
}
