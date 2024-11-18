package com.backend.devfordev.controller;

import com.backend.devfordev.apiPayload.ApiResponse;
import com.backend.devfordev.apiPayload.code.status.SuccessStatus;
import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

@Tag(name = "커뮤니티 API")
@RequiredArgsConstructor
@RestController
@RequestMapping
@Slf4j
public class CommunityController {
    private final CommunityService communityService;
    @Operation(summary = "커뮤니티 글 등록")
    @PostMapping(value = "/v1/community")
    public ResponseEntity<ApiResponse<CommunityResponse.CommunityCreateResponse>> createCommunity(@Valid @RequestBody CommunityRequest.CommunityCreateRequest request, @AuthenticationPrincipal User user){

        CommunityResponse.CommunityCreateResponse communityCreateResponse = communityService.createCommunity(request, Long.parseLong(user.getUsername()));
        ApiResponse<CommunityResponse.CommunityCreateResponse> apiResponse = ApiResponse.onSuccess(communityCreateResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @Operation(summary = "커뮤니티 글 전체 조회")
    @GetMapping(value = "/v1/community")
    public ResponseEntity<ApiResponse<List<CommunityResponse.CommunityListResponse>>> getCommunityList(
            @RequestParam(required = false) CommunityCategory category,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false, defaultValue = "recent") String sortBy
            ) {
        // 카테고리가 있을 경우 서비스에 Optional로 전달
        List<CommunityResponse.CommunityListResponse> communityList = communityService.getCommunityList(
                Optional.ofNullable(category),
                Optional.ofNullable(searchTerm),
                sortBy);

        ApiResponse<List<CommunityResponse.CommunityListResponse>> apiResponse = ApiResponse.onSuccess(communityList);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }


    @Operation(summary = "커뮤니티 글 상세 조회")
    @GetMapping(value = "/v1/community/{id}")
    public ResponseEntity<ApiResponse<CommunityResponse.CommunityDetailResponse>> getCommunityDetail(@PathVariable Long id) {
        CommunityResponse.CommunityDetailResponse communityDetail = communityService.getCommunityDetail(id);
        ApiResponse<CommunityResponse.CommunityDetailResponse> apiResponse = ApiResponse.onSuccess(communityDetail);


        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @Operation(summary = "인기 커뮤니티 Top 5 유저 조회 (좋아요 수 기준)")
    @GetMapping(value = "/v1/community/top5")
    public ResponseEntity<ApiResponse<List<CommunityResponse.CommunityTop5Response>>> getTop5UsersByTotalLikes() {
        // 인기 Top 5 유저 조회
        List<CommunityResponse.CommunityTop5Response> top5Users = communityService.getTop5UsersByTotalLikes();


        ApiResponse<List<CommunityResponse.CommunityTop5Response>> apiResponse = ApiResponse.onSuccess(top5Users);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "커뮤니티 글 수정")
    @PatchMapping(value = "/v1/community/{id}")
    public ResponseEntity<ApiResponse<CommunityResponse.CommunityUpdateResponse>> updateCommunity(@Valid @RequestBody CommunityRequest.CommunityUpdateRequest request, @PathVariable Long id, @AuthenticationPrincipal User user) {

        CommunityResponse.CommunityUpdateResponse communityUpdateResponse = communityService.updateCommunity(id, request, Long.parseLong(user.getUsername()));

        ApiResponse<CommunityResponse.CommunityUpdateResponse> apiResponse = ApiResponse.onSuccess(communityUpdateResponse);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "커뮤니티 글 삭제")
    @DeleteMapping(value = "/v1/community/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long id, @AuthenticationPrincipal User user) {
        communityService.deleteCommunity(id, Long.parseLong(user.getUsername()));

        // HTTP 상태 코드 204로 응답
        return ResponseEntity.noContent().build();
    }
}
