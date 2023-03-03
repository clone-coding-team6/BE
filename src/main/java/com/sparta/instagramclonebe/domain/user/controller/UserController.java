package com.sparta.instagramclonebe.domain.user.controller;


import com.sparta.instagramclonebe.domain.user.dto.LoginRequestDto;
import com.sparta.instagramclonebe.domain.user.dto.SignupRequestDto;
import com.sparta.instagramclonebe.domain.user.service.UserService;
import com.sparta.instagramclonebe.global.common.SuccessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SuccessResponseDto<Void> signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {//hassErrors가 boolen변수로 되어있는데 이건 오류가 있으면 true 없으면 false로 반환을 해준다.
            throw new IllegalArgumentException("로그인 조건이 맞지 않습니다.");// 여기서 오류가 떳을때 예외 처리로 이런식으로 반환을 해주라는 코드이다.
        }

        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public SuccessResponseDto<Void> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto, response);
    }
}


