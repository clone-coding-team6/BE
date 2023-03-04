package com.sparta.instagramclonebe.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    @NotBlank
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String useremail;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9~!@#$%^&*()_+=?,./<>{}\\[\\]\\-]{8,15}$", message = "비밀번호는 8~15자리 영문 대소문자(a~z, A~Z), 숫자(0~9), 특수문자를 사용하세요.")
    private String password;

    @NotBlank
    private String nickname;

    private boolean admin = false; // 관리자 권한 받을건지에 대한 on off
    private String adminToken = "";
}
