package com.backend.devfordev.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignUpResponse (

    @Schema(description = "회원 아이디", example = "1")
    Long id,
    @Schema(description = "회원 이메일", example = "xxx@naver.com")
    String email,

    @Schema(description = "회원 비밀번호", example = "bboggo1234!")
    String password,

    @Schema(description = "회원 이름", example = "BBOGGO")
    String name,

    @Schema(description = "이미지url", example = "domain.com")
    String imageUrl,

    @Schema(description = "전공", example = "정보통신공학과")
    String major,


    @Schema(description = "생년월일", example = "2000-08-19")
    String birth
) {

}
