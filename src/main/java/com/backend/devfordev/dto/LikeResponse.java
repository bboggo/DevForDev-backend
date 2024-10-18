package com.backend.devfordev.dto;

import com.backend.devfordev.domain.enums.LikeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {

    @Schema(description = "로그인한 유저 id", example = "1")
    Long memberId;

    @Schema(description = "좋아요하는 게시글의 id", example = "1")
    Long likeId;

    @Schema(description = "타입", example = "COMMUNITY")
    LikeType likeType;

    @Schema(description = "전체 좋아요 수", example = "10")
    Long likes;
}
