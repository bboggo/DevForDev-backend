package com.backend.devfordev.dto;


import com.backend.devfordev.domain.enums.CommunityCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PortfolioResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PortCreateResponse {
        Long id;
        Long member;
        String portTitle;
        String portContent;
        String portPosition;

        List<String> techStacks;
        List<String> tags;
        String portImageUrl;
        LocalDateTime createdAt;
        private List<LinkResponse> links; // 링크 리스트 추가
        private List<EducationResponse> educations;
        private List<AwardResponse> awards; // 수상 및 기타 정보 리스트

        @Getter
        @Setter
        public static class LinkResponse {
            private String type;
            private String url;
            private Integer order;


            public LinkResponse(String type, String url, Integer order) {
                this.type = type;
                this.url = url;
                this.order = order;
            }
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class EducationResponse {
            private Long id;
            private String level;
            private String institutionName;
            private String major;
            private LocalDate admissionDate;
            private LocalDate graduationDate;
            private String graduationStatus;
            private Double grade;
            private Double gradeScale;
            private Integer orderIndex; // 순서
        }


        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class AwardResponse {
            private Long id;
            private String awardType;
            private Integer orderIndex;

            // CompetitionAward 관련 필드
            private String competitionName;
            private String hostingInstitution;
            private LocalDate competitionDate;

            // CertificateAward 관련 필드
            private String certificateName;
            private String issuer;
            private Integer passingYear;

            // LanguageAward 관련 필드
            private String language;
            private String level;
            private String testName;
            private String score;
            private LocalDate obtainedDate;

            // ActivityAward 관련 필드
            private String activityName;
            private LocalDate startDate;
            private LocalDate endDate;
            private String description;

            public AwardResponse(String awardType, Integer orderIndex, String competitionName, String hostingInstitution, LocalDate competitionDate) {
                this.awardType = awardType;
                this.orderIndex = orderIndex;
                this.competitionName = competitionName;
                this.hostingInstitution = hostingInstitution;
                this.competitionDate = competitionDate;
            }

            public AwardResponse(String awardType, Integer orderIndex, String certificateName, String issuer, Integer passingYear) {
                this.awardType = awardType;
                this.orderIndex = orderIndex;
                this.certificateName = certificateName;
                this.issuer = issuer;
                this.passingYear = passingYear;
            }

            public AwardResponse(String awardType, Integer orderIndex, String language, String level, String testName, String score, LocalDate obtainedDate) {
                this.awardType = awardType;
                this.orderIndex = orderIndex;
                this.language = language;
                this.level = level;
                this.testName = testName;
                this.score = score;
                this.obtainedDate = obtainedDate;
            }

            public AwardResponse(String awardType, Integer orderIndex, String activityName, LocalDate startDate, LocalDate endDate, String description) {
                this.awardType = awardType;
                this.orderIndex = orderIndex;
                this.activityName = activityName;
                this.startDate = startDate;
                this.endDate = endDate;
                this.description = description;
            }
        }
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PortfolioListResponse {
        @Schema(description = "커뮤니티 ID", example = "1")
        Long id;
        @Schema(description = "포트폴리오 제목", example = "김민지의 포트폴리오~~")
        String portTitle;
        @Schema(description = "포지션", example = "포지션")
        String portPosition;
        @Schema(description = "태그", example = "[\"태그1\", \"태그2\", \"태그3\", \"태그4\"]")
        private List<String> tags;
        @Schema(description = "포트폴리오 이미지 url", example = "이미지url")
        String portImageUrl;
        CommunityResponse.MemberInfo member;
        @Schema(description = "작성시간", example = "2024-11-19T00:52:47.534061")
        LocalDateTime createdAt;
        @Schema(description = "답변수", example = "0")
        Long answers;
        @Schema(description = "조회수", example = "0")
        Long views;
        @Schema(description = "좋아요수", example = "0")
        Long likes;
    }
}
