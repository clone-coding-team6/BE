package com.sparta.instagramclonebe.global.excpetion.exceptionType;

import com.sparta.instagramclonebe.global.excpetion.ErrorCode;

public class S3Exception extends GlobalException{

    public S3Exception(ErrorCode errorCode){
        super(errorCode);
    }
}
