package com.backend.devfordev.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignInRequest(
        @Schema(description = "회원 이메일", example = "xxx@naver.com")
        String email,
        @Schema(description = "회원 비밀번호", example = "bboggo1234!")
        String password
) {
}
