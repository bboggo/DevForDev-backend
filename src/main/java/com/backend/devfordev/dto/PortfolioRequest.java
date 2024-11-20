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

        @Schema(description = "백엔드", example = "포지션")
        String portPosition;

        @Schema(description = "기술 스택", example = "[\"Spring\", \"Java\", \"MySQL\", \"Docker\"]")
        private List<String> techStacks;

        @Schema(description = "태그", example = "[\"태그1\", \"태그2\", \"태그3\", \"태그4\"]")
        private List<String> tags;

        @Schema(description = "포트폴리오 이미지 url", example = "이미지url")
        String portImageUrl;


        private List<LinkRequest> links; // 링크 리스트 추가
        private List<EducationRequest> educations;
        private List<AwardRequest> awards; // 수상 및 기타 정보 추가

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


        @Getter
        @Setter
        @AllArgsConstructor
        public static class AwardRequest {
            @Schema(description = "수상/자격증/어학/대외활동 유형", example = "COMPETITION")
            private String awardType;

            // CompetitionAward 관련 필드
            @Schema(description = "수상 및 공모전명", example = "정보통신공학과 학술제")
            private String competitionName;
            @Schema(description = "주최기관", example = "명지전문대학 정보통신공학과")
            private String hostingInstitution;
            @Schema(description = "공모일", example = "2023-11-01")
            private LocalDate competitionDate;

            // CertificateAward 관련 필드
            @Schema(description = "자격증명", example = "정보처리기사")
            private String certificateName;
            @Schema(description = "발행처", example = "한국산업인력공단")
            private String issuer;
            @Schema(description = "합격년월", example = "합격")
            private Integer passingYear;

            // LanguageAward 관련 필드
            @Schema(description = "언어", example = "영어")
            private String language;
            @Schema(description = "수준", example = "고급")
            private String level;
            @Schema(description = "시험명", example = "TOEIC")
            private String testName;
            @Schema(description = "점수", example = "900")
            private String score;
            @Schema(description = "취득일", example = "2022-08-15")
            private LocalDate obtainedDate;

            // ActivityAward 관련 필드
            @Schema(description = "활동명", example = "글로벌 리더십 프로그램")
            private String activityName;
            @Schema(description = "활동 시작일", example = "2024-08-01")
            private LocalDate startDate;
            @Schema(description = "활동 종료일", example = "2024-11-30")
            private LocalDate endDate;
            @Schema(description = "활동 세부사항", example = "다양한 리더십 훈련 참여")
            private String description;
        }
    }

}
