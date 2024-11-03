package com.backend.devfordev.dto;

import com.backend.devfordev.domain.enums.TeamType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
public class TeamRequest {
    @Getter
    public static class TeamCreateRequest {
        @Schema(description = "팀 제목", example = "팀 제목1")
        String teamTitle;

        @Schema(description = "팀 내용", example = "팀 모집공고 내용1")
        String teamContent;

        @Schema(description = "팀 모집 유형", example = "STUDY")
        String teamType;

        @Schema(description = "팀 모집 포지션", example = "BACKEND")
        String teamPosition;

        @Schema(description = "팀 모집 인원", example = "1")
        Long teamRecruitmentNum;


        // 기술 스택 , 태그
        @Schema(description = "기술 스택", example = "[\"Java\", \"Spring\", \"AWS\"]")
        List<String> teamTechStack;

        @Schema(description = "태그", example = "[\"Remote\", \"Full-Time\", \"Flexible\"]")
        List<String> teamTags;
    }

    @Getter
    public static class TeamAddMemberRequest {
        @Schema(description = "추가할 멤버 id", example = "1")
        private Long memberId;
    }

}
