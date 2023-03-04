package com.sparta.instagramclonebe.global.excpetion.exceptionType;

import com.sparta.instagramclonebe.global.excpetion.ErrorCode;

public class UserException extends GlobalException{

    public UserException(ErrorCode errorCode){
        super(errorCode);
    }
}
