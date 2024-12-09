package com.backend.devfordev.dto.ProjectDto;

import com.backend.devfordev.domain.enums.ProjectCategory;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectCreateResponse {
        @Schema(description = "프로젝트 ID", example = "1")
        Long id;
        @Schema(description = "작성자 ID", example = "1")
        Long writer;
        @Schema(description = "프로젝트  제목", example = "김민지의 프로젝트 ~~")
        String projectTitle;
        @Schema(description = "프로젝트 내용", example = "프로젝트 내용~~")
        String projectContent;
        @Schema(description = "프로젝트 분류", example = "APP")
        ProjectCategory projectCategory;
        @ArraySchema(
                schema = @Schema(description = "태그", example = "태그1"),
                arraySchema = @Schema(example = "[\"태그1\", \"태그2\", \"태그3\", \"태그4\"]")
        )
        List<String> tags;
        @Schema(description = "프로젝트 이미지 url", example = "이미지url")
        String projectImageUrl;
        @Schema(description = "작성시간", example = "2024-11-19T00:52:47.534061")
        LocalDateTime createdAt;
        @Schema(description = "프로젝트 링크 리스트")
        private List<ProjectResponse.ProjectCreateResponse.LinkResponse> links; // 링크 리스트 추가


        @Getter
        @Setter
        public static class LinkResponse {
            @Schema(description = "링크 타입", example = "github")
            private String type;
            @Schema(description = "url", example = "https://github.com/bboggo")
            private String url;
            @Schema(description = "정렬 순서", example = "1")
            private Integer orderIndex;


            public LinkResponse(String type, String url, Integer orderIndex) {
                this.type = type;
                this.url = url;
                this.orderIndex = orderIndex;
            }
        }
    }
}