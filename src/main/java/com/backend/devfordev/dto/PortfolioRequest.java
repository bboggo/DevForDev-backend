package com.backend.devfordev.dto;

import com.backend.devfordev.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PortfolioRequest {

    @Getter
    public static class PortfolioCreateRequest {
        @Schema(description = "포트폴리오 제목", example = "김민지의 포트폴리오~~")
        String portTitle;

        @Schema(description = "포트폴리오 내용", example = "마크다운 텍스트 부분")
        String portContent;

        @Schema(description = "포트폴리오 이미지 url", example = "이미지url")
        String portImageUrl;
    }


}
