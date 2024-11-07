package com.backend.devfordev.dto;


import com.backend.devfordev.domain.enums.CommunityCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    }
}
