package com.backend.devfordev.apiPayload;

import com.backend.devfordev.apiPayload.code.BaseErrorCode;
import com.backend.devfordev.apiPayload.exception.handler.GeneralException;

public class ExceptionHandler extends GeneralException {
    public ExceptionHandler(BaseErrorCode code) {
        super(code);
    }
}