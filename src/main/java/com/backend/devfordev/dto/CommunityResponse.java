package com.backend.devfordev.dto;

import com.backend.devfordev.domain.enums.CommunityCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

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
        LocalDateTime createdAt;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberInfo {

        @Schema(description = "멤버 id", example = "1")
        private Long id;
        @Schema(description = "프로필 사진 url", example = "domain 주소")
        private String imageUrl;
        @Schema(description = "멤버 이름", example = "김민지")
        private String name;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommunityListResponse {

        Long id;
        CommunityCategory communityCategory;
        String communityTitle;
        String communityContent;
        MemberInfo member;
        LocalDateTime createdAt;
        Long answers;
        Long views;
        Long likes;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommunityDetailResponse {

        Long id;
        CommunityCategory communityCategory;
        String communityTitle;
        String communityContent;
        MemberInfo member;
        LocalDateTime createdAt;
        Long answers;
        Long views;
        Long likes;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommunityTop5Response {
        MemberInfo member;
        Long totalLikes;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommunityUpdateResponse {
        Long id;
        CommunityCategory communityCategory;
        String communityTitle;
        String communityContent;
        Long member;
        Long communityAI;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }

}
