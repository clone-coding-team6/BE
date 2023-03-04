package com.sparta.instagramclonebe.global.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GlobalResponseDto<T> {
    private boolean success;
    private T data;

    @Builder
    public GlobalResponseDto(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
}
