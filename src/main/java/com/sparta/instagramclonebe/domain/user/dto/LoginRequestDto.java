package com.sparta.instagramclonebe.domain.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String userEmail;
    private String password;
}
