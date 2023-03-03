package com.sparta.instagramclonebe.global.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StatusResponseDto {
    private HttpStatus httpStatus;

    public StatusResponseDto(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }
}
