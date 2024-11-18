package com.backend.devfordev.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record SignInRequest(
        @Schema(description = "회원 이메일", example = "xxx@naver.com")
        @NotNull(message = "This field must not be null.")
        String email,
        @Schema(description = "회원 비밀번호", example = "bboggo1234!")
        @NotNull(message = "This field must not be null.")
        String password
) {
}
