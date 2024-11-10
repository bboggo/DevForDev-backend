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
    }
}
