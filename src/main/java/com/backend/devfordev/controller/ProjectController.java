package com.backend.devfordev.controller;

import com.backend.devfordev.apiPayload.ApiResponse;
import com.backend.devfordev.dto.PortfolioRequest;
import com.backend.devfordev.dto.PortfolioResponse;
import com.backend.devfordev.dto.ProjectRequest;
import com.backend.devfordev.dto.ProjectResponse;
import com.backend.devfordev.service.PortfolioService;
import com.backend.devfordev.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "프로젝트 API")
@RequiredArgsConstructor
@RestController
@RequestMapping
@Slf4j
public class ProjectController {
    private final ProjectService projectService;
    @Operation(summary = "프로젝트 글 등록", description = "프로젝트 글을 등록하는 api입니다.")
    @PostMapping(value = "/v1/project",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ProjectResponse.ProjectCreateResponse>> createProject(@Valid @RequestPart("request") ProjectRequest.ProjectCreateRequest request, @AuthenticationPrincipal User user,
                                                                                            @RequestPart(value = "proImage", required = false) MultipartFile proImage) {

        ProjectResponse.ProjectCreateResponse proCreateResponse = projectService.createProject(request, Long.parseLong(user.getUsername()), proImage);
        ApiResponse<ProjectResponse.ProjectCreateResponse> apiResponse = ApiResponse.onSuccess(proCreateResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
