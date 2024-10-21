package com.backend.devfordev.dto;

import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class CommunityRequest {

    @Getter
    public static class CommunityCreateRequest {
        @Schema(description = "커뮤니티 카테고리", example = "CAREER")
        String communityCategory;
        @Schema(description = "커뮤니티 제목", example = "커뮤니티 제목1")
        String communityTitle;
        @Schema(description = "커뮤니티 내용", example = "커뮤니티 내용1")
        String communityContent;
        @Schema(description = "커뮤니티 AI 답변 동의 여부", example = "1")
        Long communityAI;
        Long communityViews;
    }


    @Getter
    public static class CommunityUpdateRequest {
        @Schema(description = "커뮤니티 카테고리", example = "CAREER")
        String communityCategory;
        @Schema(description = "커뮤니티 제목", example = "커뮤니티 제목1")
        String communityTitle;
        @Schema(description = "커뮤니티 내용", example = "커뮤니티 내용1")
        String communityContent;
    }

}
