package com.sparta.instagramclonebe.global.excpetion.exceptionType;

import com.sparta.instagramclonebe.global.excpetion.ErrorCode;

public class PostException extends GlobalException{

    public PostException(ErrorCode errorCode){
        super(errorCode);
    }
}
