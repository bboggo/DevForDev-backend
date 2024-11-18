package com.backend.devfordev.dto;

import com.backend.devfordev.domain.enums.TeamType;
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
        TeamType teamType;
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
        TeamType teamType;
        String teamPosition;
        Long teamRecruitmentNum;
        List<String> teamTechStack;
        List<String> teamTags;
        LocalDateTime createdAt;
        Boolean teamIsActive;
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
        TeamType teamType;
        String teamPosition;
        Long teamRecruitmentNum;
        List<String> teamTechStack;
        List<String> teamTags;
        LocalDateTime createdAt;
        Boolean teamIsActive;
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

    @Getter
    @AllArgsConstructor
    public static class TeamMemberListWithIdResponse {
        private Long teamId; // 팀 ID 한 번만 포함
        private List<TeamMemberListResponse> members; // 팀 멤버 리스트
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamMemberListResponse {
        private Long id;
        private CommunityResponse.MemberInfo member; // 팀원의 정보 (MemberInfo를 사용)
    }
}
