package com.sparta.instagramclonebe.domain.user.service;

import com.sparta.instagramclonebe.domain.user.dto.LoginRequestDto;
import com.sparta.instagramclonebe.domain.user.dto.SignupRequestDto;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.domain.user.entity.UserRoleEnum;
import com.sparta.instagramclonebe.domain.user.repository.UserRepository;
import com.sparta.instagramclonebe.global.redis.RefreshToken;
import com.sparta.instagramclonebe.global.redis.RefreshTokenRepository;
import com.sparta.instagramclonebe.global.redis.TokenDto;
import com.sparta.instagramclonebe.global.util.ResponseUtils;
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.excpetion.ErrorCode;
import com.sparta.instagramclonebe.global.excpetion.exceptionType.UserException;
import com.sparta.instagramclonebe.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<GlobalResponseDto<Void>> signup(SignupRequestDto signupRequestDto) { // 회원 가입
        String userEmail = signupRequestDto.getUserEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword()); // 비밀번호 암호화
        String nickname = signupRequestDto.getNickname();

        //회원 중복 확인
        Optional<User> found = userRepository.findByUserEmail(userEmail);
        if (found.isPresent()) {
            throw new UserException(ErrorCode.USER_EMAIL_EXIST);
        }

        // 닉네임 중복 확인
        Optional<User> nickNameFound = userRepository.findByNickname(nickname);
        if (nickNameFound.isPresent()) {
            throw new UserException(ErrorCode.USER_NICKNAME_EXIST);
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;


        User user = User.of(userEmail, password, role, nickname);
        userRepository.save(user);
        return new ResponseEntity<>(ResponseUtils.ok(null), HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto<Void>> login(LoginRequestDto loginRequestDto, HttpServletResponse response) { // 로그인
        String userEmail = loginRequestDto.getUserEmail();
        String password = loginRequestDto.getPassword();

        //사용자 확인
        if (userRepository.findByUserEmail(userEmail).isEmpty()) {
            throw new UserException(ErrorCode.USER_ACCOUNT_NOT_EXIST);
        }

        //비밀번호 중복 확인
        User user = userRepository.findByUserEmail(userEmail).get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException(ErrorCode.PASSWORD_MISMATCH);
        }

        TokenDto tokenDto = jwtUtil.createAllToken(userEmail);

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findAllByUserEmail(userEmail);

        if(refreshToken.isPresent()) { // 유저의 리프레시 토큰이 있으면 기존에 있는걸 새로운 리프레시 토큰으로 갈아 끼워줌
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else { // 리프레시 토큰이 없다면 인증할때 만든 객체랑 새로운 리프레시 토큰을 같이 넣어줌
            RefreshToken newRefreshToken = new RefreshToken(tokenDto.getRefreshToken(), userEmail);
            refreshTokenRepository.save(newRefreshToken);
        }

        // Authorization 에 AccessToken 설정
        String token = jwtUtil.createToken(user.getUserEmail(), "Access");
        response.addHeader(JwtUtil.ACCESS_TOKEN, token);

        // 쿠키 설정
        Cookie cookie = new Cookie("token", token.substring(7));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        // Authorization 에 RefreshToken 설정
        String tokens = jwtUtil.createToken(user.getUserEmail(), "Refresh");
        response.addHeader(JwtUtil.REFRESH_TOKEN, token);

        // 쿠키 설정
        Cookie cookies = new Cookie("token", tokens.substring(7));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(604800);
        response.addCookie(cookies);

        return new ResponseEntity<>(ResponseUtils.ok(null), HttpStatus.OK);
    }
}

