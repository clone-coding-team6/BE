package com.sparta.instagramclonebe.global.response.exceptionType;

import com.sparta.instagramclonebe.global.response.CustomStatusCode;
import lombok.Getter;

@Getter
public class CommentException extends GlobalException{

    public CommentException(CustomStatusCode statusCode){
        super(statusCode);
    }
}
