package com.sparta.instagramclonebe.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.instagramclonebe.global.dto.GlobalResponseDto;
import com.sparta.instagramclonebe.global.response.CustomStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        CustomStatusCode exception = (CustomStatusCode) request.getAttribute("exception");

        if(exception.equals(CustomStatusCode.NOT_VALID_REQUEST)){
            exceptionHandler(response, CustomStatusCode.NOT_VALID_REQUEST);
            return;
        }

        if (exception.equals(CustomStatusCode.TOKEN_NOT_FOUND)) {
            exceptionHandler(response, CustomStatusCode.TOKEN_NOT_FOUND);
            return;
        }

        if (exception.equals(CustomStatusCode.NOT_VALID_TOKEN)) {
            exceptionHandler(response, CustomStatusCode.NOT_VALID_TOKEN);
            return;
        }

        if (exception.equals(CustomStatusCode.USER_NOT_FOUND)) {
            exceptionHandler(response, CustomStatusCode.USER_NOT_FOUND);
        }
    }

    public void exceptionHandler(HttpServletResponse response, CustomStatusCode error) {
        response.setStatus(error.getStatusCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(GlobalResponseDto.of(error));
            response.getWriter().write(json);
            log.error(error.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
