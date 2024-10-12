package com.backend.devfordev.dto;

import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.enums.CommunityCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommunityResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommunityCreateResponse {
        Long id;
        CommunityCategory communityCategory;
        String communityTitle;
        String communityContent;
        Long member;
        Long communityAI;
    }

}
