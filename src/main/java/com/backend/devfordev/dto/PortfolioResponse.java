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
}
