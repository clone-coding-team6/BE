package com.sparta.instagramclonebe.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 4~10자리 영문 소문자(a~z),숫자(0~9)를 사용하세요.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9~!@#$%^&*()_+=?,./<>{}\\[\\]\\-]{8,15}$", message = "비밀번호는 8~15자리 영문 대소문자(a~z, A~Z), 숫자(0~9), 특수문자를 사용하세요.")
    private String password;

    @NotBlank
    private String nickname;

    private boolean admin = false; // 관리자 권한 받을건지에 대한 on off
    private String adminToken = "";
}
