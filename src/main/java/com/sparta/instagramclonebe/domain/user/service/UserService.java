package com.sparta.instagramclonebe.domain.user.service;

import com.sparta.instagramclonebe.domain.user.dto.LoginRequestDto;
import com.sparta.instagramclonebe.domain.user.dto.SignupRequestDto;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.domain.user.entity.UserRoleEnum;
import com.sparta.instagramclonebe.domain.user.repository.UserRepository;
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.jwt.JwtUtil;
import com.sparta.instagramclonebe.global.response.CustomStatusCode;
import com.sparta.instagramclonebe.global.response.exceptionType.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public ResponseEntity<GlobalResponseDto> signup(SignupRequestDto signupRequestDto) {
        String userEmail = signupRequestDto.getUserEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickname = signupRequestDto.getNickname();

        //회원 중복 확인
        Optional<User> found = userRepository.findByUserEmail(userEmail);
        if (found.isPresent()) {
            throw new UserException(CustomStatusCode.USER_EMAIL_EXIST);
        }

        // 닉네임 중복 확인
        Optional<User> nickNameFound = userRepository.findByNickname(nickname);
        if (nickNameFound.isPresent()) {
            throw new UserException(CustomStatusCode.USER_NICKNAME_EXIST);
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;


        User user = User.of(userEmail, password, role, nickname);
        userRepository.save(user);
        return ResponseEntity.ok(GlobalResponseDto.of(CustomStatusCode.SIGN_UP_SUCCESS));

    }

    // 로그인
    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String userEmail = loginRequestDto.getUserEmail();
        String password = loginRequestDto.getPassword();

        if (userRepository.findByUserEmail(userEmail).isEmpty()) {
            throw new UserException(CustomStatusCode.USER_ACCOUNT_NOT_EXIST);
        }

        User user = userRepository.findByUserEmail(userEmail).get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException(CustomStatusCode.PASSWORD_MISMATCH);
        }

        String token = jwtUtil.createToken(user.getUserEmail(), user.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        Cookie cookie = new Cookie("token", token.substring(7));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return ResponseEntity.ok(GlobalResponseDto.of(CustomStatusCode.LOG_IN_SUCCESS));
    }

}
