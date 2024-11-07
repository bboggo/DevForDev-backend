package com.backend.devfordev.dto;


import com.backend.devfordev.domain.enums.CommunityCategory;
import lombok.*;

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
        String portImageUrl;
        LocalDateTime createdAt;
        private List<LinkResponse> links; // 링크 리스트 추가

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
    }
}
