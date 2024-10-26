package com.backend.devfordev.dto;

import com.backend.devfordev.domain.enums.CommunityCategory;
import io.swagger.v3.oas.annotations.media.Schema;
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
        String teamTitle;
        String teamContent;
        String teamType;
        String teamPosition;
        Long teamRecruitmentNum;
        List<String> teamTechStack;
        List<String> teamTags;
        LocalDateTime createdAt;
    }

}
