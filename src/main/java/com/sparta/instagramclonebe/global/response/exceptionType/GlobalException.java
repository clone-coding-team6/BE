package com.sparta.instagramclonebe.global.response.exceptionType;

import com.sparta.instagramclonebe.global.response.CustomStatusCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalException extends RuntimeException{
    private CustomStatusCode statusCode;

    public GlobalException (CustomStatusCode statusCode){
        this.statusCode = statusCode;
    }
}
