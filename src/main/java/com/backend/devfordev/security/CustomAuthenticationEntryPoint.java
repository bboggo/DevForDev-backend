package com.backend.devfordev.security;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");

        if(exception == null) {
            setResponse(response, ErrorStatus._INTERNAL_SERVER_ERROR);
        } else if (exception.equals(ErrorStatus.INVALID_JWT_TOKEN.getMessage())) {
            setResponse(response, ErrorStatus.INVALID_JWT_TOKEN);
        } else if (exception.equals(ErrorStatus.EXPIRED_JWT_ERROR.getMessage())) {
            setResponse(response, ErrorStatus.EXPIRED_JWT_ERROR);
        } else if (exception.equals(ErrorStatus.UNSUPPORTED_JWT_TOKEN.getMessage())) {
            setResponse(response, ErrorStatus.UNSUPPORTED_JWT_TOKEN);
        } else if (exception.equals(ErrorStatus.USER_AUTH_ERROR.getMessage())) {
            setResponse(response, ErrorStatus.USER_AUTH_ERROR);
        } else if(exception.equals(ErrorStatus.TOKEN_MISSING_ERROR.getMessage())) {
            setResponse(response, ErrorStatus.TOKEN_MISSING_ERROR);
        }
    }

//    @Override
//    public void commences(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
//        // 예외 처리 시 사용될 ErrorReasonDTO 생성
//        ErrorReasonDTO error = ErrorReasonDTO.builder()
//                .httpStatus(HttpStatus.UNAUTHORIZED)
//                .isSuccess(false)
//                .code("UNAUTHORIZED")
//                .message("Unauthorized access")
//                .build();
//
//        setResponse(response, error);
//    }

    private void setResponse(HttpServletResponse response, ErrorStatus error) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(error.getHttpStatus().value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }
}
