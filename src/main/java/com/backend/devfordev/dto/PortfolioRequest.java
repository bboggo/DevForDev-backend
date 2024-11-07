package com.backend.devfordev.dto;

import com.backend.devfordev.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
        private List<EducationRequest> educations;

        @Getter
        @Setter
        public static class LinkRequest {

            @Schema(description = "링크 타입", example = "github")
            private String type;
            @Schema(description = "url", example = "https://github.com/bboggo")
            private String url;

        }


        @Getter
        @Setter
        @AllArgsConstructor
        public static class EducationRequest {
            @Schema(description = "학력구분", example = "대학(2,3년제)")
            private String level;

            @Schema(description = "학교명", example = "명지전문대학")
            private String institutionName;

            @Schema(description = "전공", example = "정보통신공학과")
            private String major;

            @Schema(description = "입학일", example = "2020-03-02")
            private LocalDate admissionDate;

            @Schema(description = "졸업일", example = "2024-02-27")
            private LocalDate graduationDate;

            @Schema(description = "졸업여부선택", example = "졸업")
            private String graduationStatus;

            @Schema(description = "학점", example = "4.25")
            private Double grade;

            @Schema(description = "기준학점", example = "4.5")
            private Double gradeScale;
        }
    }


}
