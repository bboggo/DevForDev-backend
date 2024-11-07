package com.backend.devfordev.dto;

import com.backend.devfordev.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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


        private List<LinkRequest> links; // 링크 리스트 추가

        @Getter
        @Setter
        public static class LinkRequest {

            @Schema(description = "링크 타입", example = "github")
            private String type;
            @Schema(description = "url", example = "https://github.com/bboggo")
            private String url;

        }
    }


}
