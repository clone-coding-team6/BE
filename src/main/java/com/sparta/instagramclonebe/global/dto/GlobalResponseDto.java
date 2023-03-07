package com.sparta.instagramclonebe.global.dto;

import com.sparta.instagramclonebe.global.response.CustomStatusCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GlobalResponseDto {
    private int statusCode;
    private String message;
    private Object data;

    @Builder
    private GlobalResponseDto(CustomStatusCode statusCode, Object data) {
        this.statusCode = statusCode.getStatusCode();
        this.message = statusCode.getMessage();
        this.data = data;
    }

    public GlobalResponseDto(int statusCode, String message, Object data){
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static GlobalResponseDto of(CustomStatusCode statusCode, Object data){
        return GlobalResponseDto.builder()
                .statusCode(statusCode)
                .data(data)
                .build();
    }

    public static GlobalResponseDto of(CustomStatusCode statusCode){
        return GlobalResponseDto.builder()
                .statusCode(statusCode)
                .build();
    }
}
