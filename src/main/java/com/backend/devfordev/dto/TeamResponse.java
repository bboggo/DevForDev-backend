package com.backend.devfordev.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class TeamResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamCreateResponse {
        Long id;
        String teamTitle;
        String teamContent;
        String teamType;
        String teamPosition;
        Long teamRecruitmentNum;
        List<String> teamTechStack;
        List<String> teamTags;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamListResponse {
        Long id;
        CommunityResponse.MemberInfo member;
        String teamTitle;
        String teamContent;
        String teamType;
        String teamPosition;
        Long teamRecruitmentNum;
        List<String> teamTechStack;
        List<String> teamTags;
        LocalDateTime createdAt;
        Long teamIsActive;
        Long views;
        Long answers;
        Long likes;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamDetailResponse {
        Long id;
        CommunityResponse.MemberInfo member;
        String teamTitle;
        String teamContent;
        String teamType;
        String teamPosition;
        Long teamRecruitmentNum;
        List<String> teamTechStack;
        List<String> teamTags;
        LocalDateTime createdAt;
        Long teamIsActive;
        Long views;
        Long answers;
        Long likes;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamAddMemberResponse {
        Long id;
        Long teamId;
        Long memberId;
    }
}
