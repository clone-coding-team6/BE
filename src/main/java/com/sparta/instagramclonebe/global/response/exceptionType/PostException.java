package com.sparta.instagramclonebe.global.response.exceptionType;

import com.sparta.instagramclonebe.global.response.CustomStatusCode;

public class PostException extends GlobalException{

    public PostException(CustomStatusCode statusCode){
        super(statusCode);
    }
}
