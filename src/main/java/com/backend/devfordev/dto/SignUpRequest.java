package com.backend.devfordev.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @Schema(description = "회원 이메일", example = "xxx@naver.com")
        @NotNull
        @Email(message = "올바른 형식의 이메일 주소여야 합니다.")
        String email,

        @Schema(description = "회원 비밀번호", example = "bboggo1234!")
        @NotNull
        String password,

        @Schema(description = "회원 이름", example = "BBOGGO")
        @NotNull
        String name,

        @Schema(description = "이미지url", example = "domain.com")
        String imageUrl,

        @Schema(description = "생년월일", example = "2000-08-19")
        @NotNull
        String birth
) {
}
