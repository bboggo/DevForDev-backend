package com.backend.devfordev.dto;


import com.backend.devfordev.domain.enums.ProjectCategory;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ProjectRequest {

    @Getter
    @Setter
    public static class ProjectCreateRequest {
        @NotNull(message = "This field must not be null.")
        @Schema(description = "프로젝트  제목", example = "김민지의 프로젝트 ~~")
        String projectTitle;
        @NotNull(message = "This field must not be null.")
        @Schema(description = "프로젝트 내용", example = "프로젝트 내용~~")
        String projectContent;
        @Schema(description = "프로젝트 분류", example = "APP")
        @NotNull(message = "This field must not be null.")
        ProjectCategory projectCategory;
        @ArraySchema(
                schema = @Schema(description = "태그", example = "태그1"),
                arraySchema = @Schema(example = "[\"태그1\", \"태그2\", \"태그3\", \"태그4\"]")
        )
        List<String> tags;
//        @Schema(description = "프로젝트 이미지 url", example = "이미지url")
//        String projectImageUrl;
        @Schema(description = "프로젝트 링크 리스트")
        private List<LinkRequest> links;

        @Getter
        @Setter
        public static class LinkRequest {

            @Schema(description = "링크 타입", example = "github")
            private String type;
            @Schema(description = "url", example = "https://github.com/bboggo")
            private String url;

        }
    }
}
