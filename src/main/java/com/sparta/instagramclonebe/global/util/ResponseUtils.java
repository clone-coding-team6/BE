package com.sparta.instagramclonebe.global.util;

import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;

public class ResponseUtils {
    public static <T> GlobalResponseDto<T> ok(T data) {
        return new GlobalResponseDto<T>(true, data);
    }
    public static <T> GlobalResponseDto<T> badRequest(T data) {
        return new GlobalResponseDto<T>(false, data);
    }
}
