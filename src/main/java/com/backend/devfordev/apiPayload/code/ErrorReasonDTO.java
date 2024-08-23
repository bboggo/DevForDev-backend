package com.backend.devfordev.apiPayload.code;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReasonDTO {

    private HttpStatus httpStatus;

    private final boolean isSuccess;
    private final String code;
    private final String message;



    public boolean getIsSuccess(){return isSuccess;}


}