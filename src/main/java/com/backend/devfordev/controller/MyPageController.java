package com.backend.devfordev.controller;

import com.backend.devfordev.apiPayload.ApiResponse;
import com.backend.devfordev.apiPayload.code.status.SuccessStatus;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.dto.MyPageInfoRequest;
import com.backend.devfordev.dto.MyPageInfoResponse;
import com.backend.devfordev.service.MyPageService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "마이페이지 API")
@RequiredArgsConstructor
@RestController
@RequestMapping
@Slf4j
public class MyPageController {
    private final MyPageService myPageService;

    @Operation(summary = "마이페이지 프로필 조회")
    @GetMapping("/v1/my-page/profile")
    public ResponseEntity<ApiResponse<MyPageInfoResponse.ProfileResponse>> getProfileInfo(@AuthenticationPrincipal User user) {
        MyPageInfoResponse.ProfileResponse memberResponse = myPageService.getProfile(Long.parseLong(user.getUsername()));
        ApiResponse<MyPageInfoResponse.ProfileResponse> apiResponse =ApiResponse.onSuccess(memberResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @Operation(summary = "마이페이지 프로필 저장")
    @PatchMapping(value = "/v1/my-page/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<MyPageInfoResponse.ProfileUpdateResponse>> updateProfileInfo(@Valid @RequestPart("request") MyPageInfoRequest.ProfileUpdateRequest request, @AuthenticationPrincipal User user,
                                                                                                   @RequestPart("profileImage") MultipartFile profileImage) {

        MyPageInfoResponse.ProfileUpdateResponse profileUpdateResponse = myPageService.updateProfile(Long.parseLong(user.getUsername()), request, profileImage);

        ApiResponse<MyPageInfoResponse.ProfileUpdateResponse> apiResponse = ApiResponse.onSuccess(profileUpdateResponse);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "마이페이지 비밀번호 수정")
    @PatchMapping(value = "/v1/my-page/password", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updatePassword(@Valid @RequestBody MyPageInfoRequest.PasswordUpdateRequest request, @AuthenticationPrincipal User user) {

        myPageService.updatePassword(Long.parseLong(user.getUsername()), request);
        ApiResponse apiResponse = ApiResponse.builder()
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message("비밀번호 수정이 완료되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}

