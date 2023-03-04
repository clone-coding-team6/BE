package com.sparta.instagramclonebe.global.excpetion;

import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.excpetion.exceptionType.*;
import com.sparta.instagramclonebe.global.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<GlobalResponseDto<String>> handleCommentException(UserException ex){
        ErrorCode errorCode = ex.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(ResponseUtils.badRequest(errorCode.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<GlobalResponseDto<String>> handlePostException(PostException ex){
        ErrorCode errorCode = ex.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(ResponseUtils.badRequest(errorCode.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<GlobalResponseDto<String>> handleCommentException(CommentException ex){
        ErrorCode errorCode = ex.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(ResponseUtils.badRequest(errorCode.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<GlobalResponseDto<String>> handleS3Exception(S3Exception ex){
        ErrorCode errorCode = ex.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(ResponseUtils.badRequest(errorCode.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<GlobalResponseDto<String>> handleGlobalException(GlobalException ex){
        ErrorCode errorCode = ex.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(ResponseUtils.badRequest(errorCode.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodException(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<String> allMessages = new ArrayList<>();
        for (ObjectError error : allErrors) {
            log.error(error.getDefaultMessage());
            allMessages.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(allMessages, HttpStatus.BAD_REQUEST);
    }
}
