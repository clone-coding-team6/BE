package com.sparta.instagramclonebe.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

        String accessToken = jwtUtil.resolveToken(request, "Access");
        String refreshToken = jwtUtil.resolveToken(request, "Refresh");

        if(accessToken != null) {
            if(!jwtUtil.tokenValidation(accessToken)){
                jwtExceptionHandler(response, "AccessToken Expired", HttpStatus.UNAUTHORIZED);
                return;
            }
            Claims info = jwtUtil.getUserInfoFromToken(accessToken);
            setAuthentication(info.getSubject());
        }else if(refreshToken != null) {
            if(!jwtUtil.refreshTokenValidation(refreshToken)){
                jwtExceptionHandler(response, "RefreshToken Expired", HttpStatus.UNAUTHORIZED);
                return;
            }
            Claims info = jwtUtil.getUserInfoFromToken(refreshToken);
            setAuthentication(info.getSubject());
        }
        filterChain.doFilter(request,response);
    }

    private void setAuthentication(String userEmail) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(userEmail);// 인증 객체 생성
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(ResponseEntity.status(HttpStatus.UNAUTHORIZED));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
