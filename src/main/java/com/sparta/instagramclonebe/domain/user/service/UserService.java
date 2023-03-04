package com.sparta.instagramclonebe.domain.user.service;

import com.sparta.instagramclonebe.domain.user.dto.LoginRequestDto;
import com.sparta.instagramclonebe.domain.user.dto.SignupRequestDto;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.domain.user.entity.UserRoleEnum;
import com.sparta.instagramclonebe.domain.user.repository.UserRepository;
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

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<GlobalResponseDto<Void>> signup(SignupRequestDto signupRequestDto) { // 회원 가입
        String useremail = signupRequestDto.getUseremail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword()); // 비밀번호 암호화
        String nickname = signupRequestDto.getNickname();

        //회원 중복 확인
        Optional<User> found = userRepository.findByUseremail(useremail);
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


        User user = User.of(useremail, password, role, nickname);
        userRepository.save(user);
        return new ResponseEntity<>(ResponseUtils.ok(null), HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto<Void>> login(LoginRequestDto loginRequestDto, HttpServletResponse response) { // 로그인
        String useremail = loginRequestDto.getUseremail();
        String password = loginRequestDto.getPassword();

        //사용자 확인
        if (userRepository.findByUseremail(useremail).isEmpty()) {
            throw new UserException(ErrorCode.USER_ACCOUNT_NOT_EXIST);
        }

        //비밀번호 중복 확인
        User user = userRepository.findByUseremail(useremail).get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException(ErrorCode.PASSWORD_MISMATCH);
        }

        // Authorization 에 token 설정
        String token = jwtUtil.createToken(user.getUseremail(), user.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        // 쿠키 설정
        Cookie cookie = new Cookie("token", token.substring(7));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return new ResponseEntity<>(ResponseUtils.ok(null), HttpStatus.OK);
    }

}
