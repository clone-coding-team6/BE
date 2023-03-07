package com.sparta.instagramclonebe.global.response.exceptionType;

import com.sparta.instagramclonebe.global.response.CustomStatusCode;

public class S3Exception extends GlobalException{

    public S3Exception(CustomStatusCode statusCode){
        super(statusCode);
    }
}
