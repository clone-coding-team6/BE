package com.sparta.instagramclonebe.global.jwt;

import com.sparta.instagramclonebe.global.response.CustomStatusCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        // 토큰이 null 이면 다음 필터로 넘어간다
        if (token == null) {
            request.setAttribute("exception", CustomStatusCode.TOKEN_NOT_FOUND);
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰이 유효하지 않으면 다음 필터로 넘어간다
        if (!jwtUtil.validateToken(token)) {
            request.setAttribute("exception", CustomStatusCode.NOT_VALID_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }

        // 유효한 토큰이라면, 토큰으로부터 사용자 정보를 가져온다.
        Claims info = jwtUtil.getUserInfoFromToken(token);
        try {
            setAuthentication(info.getSubject());   // 사용자 정보로 인증 객체 만들기
        } catch (UsernameNotFoundException e) {
            request.setAttribute("exception", CustomStatusCode.USER_NOT_FOUND);
        }
        // 다음 필터로 넘어간다.
        filterChain.doFilter(request, response);
}

    private void setAuthentication(String userEmail) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(userEmail);// 인증 객체 생성
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }
}
