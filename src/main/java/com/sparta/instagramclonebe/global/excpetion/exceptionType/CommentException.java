package com.sparta.instagramclonebe.global.excpetion.exceptionType;

import com.sparta.instagramclonebe.global.excpetion.ErrorCode;
import lombok.Getter;

@Getter
public class CommentException extends GlobalException{

    public CommentException(ErrorCode errorCode){
        super(errorCode);
    }
}
