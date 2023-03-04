package com.sparta.instagramclonebe.domain.user.service;

import com.sparta.instagramclonebe.domain.user.dto.LoginRequestDto;
import com.sparta.instagramclonebe.domain.user.dto.SignupRequestDto;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.domain.user.entity.UserRoleEnum;
import com.sparta.instagramclonebe.domain.user.repository.UserRepository;
import com.sparta.instagramclonebe.global.common.ResponseUtils;
import com.sparta.instagramclonebe.global.common.SuccessResponseDto;
import com.sparta.instagramclonebe.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SuccessResponseDto<Void> signup(SignupRequestDto signupRequestDto) { // 회원 가입
        String useremail = signupRequestDto.getUseremail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword()); // 비밀번호 암호화
        String nickname = signupRequestDto.getNickname();

        //회원 중복 확인
        Optional<User> found = userRepository.findByUseremail(useremail);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 아이디 입니다.");
        }

        // 닉네임 중복 확인
        Optional<User> nickNameFound = userRepository.findByNickname(nickname);
        if (nickNameFound.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;


        User user = User.of(useremail, password, role, nickname);
        userRepository.save(user);
        return ResponseUtils.ok();

    }

    @Transactional(readOnly = true)
    public SuccessResponseDto<Void> login(LoginRequestDto loginRequestDto, HttpServletResponse response) { // 로그인
        String useremail = loginRequestDto.getUseremail();
        String password = loginRequestDto.getPassword();

        //사용자 확인
        if (userRepository.findByUseremail(useremail).isEmpty()) {
            throw new IllegalArgumentException("등록된 사용자가 없습니다.");
        }

        //비밀번호 중복 확인
        User user = userRepository.findByUseremail(useremail).get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUseremail(), user.getRole()));
        return ResponseUtils.ok();
    }

}
