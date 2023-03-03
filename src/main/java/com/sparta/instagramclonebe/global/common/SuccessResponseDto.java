package com.sparta.instagramclonebe.global.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SuccessResponseDto<T> {
    private boolean success;
    private T data;

    @Builder
    public SuccessResponseDto(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
}
