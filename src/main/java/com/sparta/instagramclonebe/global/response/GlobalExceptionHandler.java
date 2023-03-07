package com.sparta.instagramclonebe.global.response;

import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.response.exceptionType.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<GlobalResponseDto> handleCommentException(UserException ex){
        CustomStatusCode statusCode = ex.getStatusCode();
        log.error(statusCode.getMessage());
        return ResponseEntity.ok(GlobalResponseDto.of(statusCode));
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<GlobalResponseDto> handlePostException(PostException ex){
        CustomStatusCode statusCode = ex.getStatusCode();
        log.error(statusCode.getMessage());
        return ResponseEntity.ok(GlobalResponseDto.of(statusCode));
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<GlobalResponseDto> handleCommentException(CommentException ex){
        CustomStatusCode statusCode = ex.getStatusCode();
        log.error(statusCode.getMessage());
        return ResponseEntity.ok(GlobalResponseDto.of(statusCode));
    }

    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<GlobalResponseDto> handleS3Exception(S3Exception ex){
        CustomStatusCode statusCode = ex.getStatusCode();
        log.error(statusCode.getMessage());
        return ResponseEntity.ok(GlobalResponseDto.of(statusCode));
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<GlobalResponseDto> handleGlobalException(GlobalException ex){
        CustomStatusCode statusCode = ex.getStatusCode();
        log.error(statusCode.getMessage());
        return ResponseEntity.ok(GlobalResponseDto.of(statusCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponseDto> handleMethodException(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error(message);
        return ResponseEntity.ok(new GlobalResponseDto(HttpStatus.BAD_REQUEST.value(), message, null));
    }
}
