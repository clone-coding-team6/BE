package com.sparta.instagramclonebe.global.response.exceptionType;

import com.sparta.instagramclonebe.global.response.CustomStatusCode;

public class UserException extends GlobalException{

    public UserException(CustomStatusCode statusCode){
        super(statusCode);
    }
}
