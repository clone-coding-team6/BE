package com.sparta.instagramclonebe.global.excpetion.exceptionType;

import com.sparta.instagramclonebe.global.excpetion.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalException extends RuntimeException{
    private ErrorCode errorCode;

    public GlobalException (ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
