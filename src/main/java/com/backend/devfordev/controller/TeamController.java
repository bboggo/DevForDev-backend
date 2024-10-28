package com.backend.devfordev.controller;

import com.backend.devfordev.apiPayload.ApiResponse;
import com.backend.devfordev.apiPayload.code.status.SuccessStatus;

import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.dto.TeamRequest;
import com.backend.devfordev.dto.TeamResponse;
import com.backend.devfordev.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "팀 API")
@RequiredArgsConstructor
@RestController
@RequestMapping
@Slf4j
public class TeamController {
    private final TeamService teamService;

    @Operation(summary = "팀 모집글 등록")
    @PostMapping(value = "/v1/team")
    public ResponseEntity<ApiResponse> createTeam(@RequestBody TeamRequest.TeamCreateRequest request, @AuthenticationPrincipal User user){

        ApiResponse apiResponse = ApiResponse.builder()
                .result(teamService.createTeam(request, Long.parseLong(user.getUsername())))
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "팀 모집 마감")
    @PatchMapping("v1/team/{teamId}/close")
    public ResponseEntity<ApiResponse> closeRecruitment(
            @PathVariable Long teamId,
            @AuthenticationPrincipal User user) {

        teamService.closeRecruitment(teamId, Long.parseLong(user.getUsername()));

        ApiResponse response = ApiResponse.builder()
                .isSuccess(true)
                .code(SuccessStatus._OK.getCode())
                .message("모집 상태가 마감되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "팀 모집글 전체 조회")
    @GetMapping(value = "/v1/team")
    public ResponseEntity<ApiResponse> getTeamList(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false, defaultValue = "recent") String sortBy
    ) {
        // 카테고리가 있을 경우 서비스에 Optional로 전달
        List<TeamResponse.TeamListResponse> teamList = teamService.getTeamList(
                Optional.ofNullable(searchTerm),
                sortBy);

        ApiResponse apiResponse = ApiResponse.builder()
                .result(teamList)
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }


    @Operation(summary = "팀 모집글 상세 조회")
    @GetMapping(value = "/v1/team/{id}")
    public ResponseEntity<ApiResponse> getTeamDetail(@PathVariable Long id) {
        TeamResponse.TeamDetailResponse teamDetail = teamService.getTeamDetail(id);

        ApiResponse apiResponse = ApiResponse.builder()
                .result(teamDetail)
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "팀 모집글 삭제")
    @DeleteMapping(value = "/v1/team/{id}")
    public ResponseEntity<ApiResponse> deleteTeam(@PathVariable Long id, @AuthenticationPrincipal User user) {
        teamService.deleteTeam(id, Long.parseLong(user.getUsername()));

        ApiResponse apiResponse = ApiResponse.builder()
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
