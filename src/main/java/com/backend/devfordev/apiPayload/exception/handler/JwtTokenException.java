package com.backend.devfordev.apiPayload.exception.handler;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import lombok.Builder;

public class JwtTokenException extends RuntimeException {

    private final ErrorStatus errorCode;

    @Builder
    public JwtTokenException(String message, ErrorStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public JwtTokenException(ErrorStatus errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
