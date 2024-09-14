package com.backend.devfordev.apiPayload.code.status;


import com.backend.devfordev.apiPayload.code.BaseErrorCode;
import com.backend.devfordev.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(BAD_REQUEST, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    // For test
    TEMP_EXCEPTION(BAD_REQUEST, "TEMP4001", "이거는 테스트"),
    TOKEN_MISSING_ERROR(BAD_REQUEST, "G018", "Token is missing."),
    EXPIRED_JWT_ERROR(BAD_REQUEST, "G013", "The provided JWT token is expired"),
    USER_AUTH_ERROR(BAD_REQUEST, "G015", "User authentication failed"),
    UNSUPPORTED_JWT_TOKEN(BAD_REQUEST,"G017", "The provided JWT token is not supported"),
    TOKEN_TIME_OUT(UNAUTHORIZED, "AUTH4011", "토큰이 만료되었습니다."),
    INVALID_JWT_TOKEN(UNAUTHORIZED, "AUTH4012", "토큰 유효성 검사 실패 또는 거부된 토큰입니다."),
    LOGIN_FAILED_PASSWORD_INCORRECT(UNAUTHORIZED,"AUTH4013", "비밀번호가 틀립니다."),

    // 회원가입

    DUPLICATED_NAME(BAD_REQUEST, "MEMBER001", "Nickname is duplicated"),
    DUPLICATED_EMAIL(BAD_REQUEST, "MEMBER002", "Email is duplicated");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}