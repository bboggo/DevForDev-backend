package com.backend.devfordev.controller;

import com.backend.devfordev.apiPayload.ApiResponse;
import com.backend.devfordev.apiPayload.code.status.SuccessStatus;

import com.backend.devfordev.dto.TeamRequest;
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

@Tag(name = "팀 관련 API")
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

    @PatchMapping("/{teamId}/close")
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
}
