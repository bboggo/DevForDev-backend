package com.backend.devfordev.global.exception;

import com.backend.devfordev.global.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessExceptionHandler extends RuntimeException {

    private final ErrorCode errorCode;

    @Builder
    public BusinessExceptionHandler(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public BusinessExceptionHandler(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
