package com.sparta.instagramclonebe.global.common;

public class ResponseUtils {

    public static <T> SuccessResponseDto<T> ok() {
        return new SuccessResponseDto<T>(true, null);
    }
}
