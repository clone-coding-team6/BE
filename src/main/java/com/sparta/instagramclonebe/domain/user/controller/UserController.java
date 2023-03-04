package com.sparta.instagramclonebe.domain.user.controller;


import com.sparta.instagramclonebe.domain.user.dto.LoginRequestDto;
import com.sparta.instagramclonebe.domain.user.dto.SignupRequestDto;
import com.sparta.instagramclonebe.domain.user.service.UserService;
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GlobalResponseDto<Void>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResponseDto<Void>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto, response);
    }
}


