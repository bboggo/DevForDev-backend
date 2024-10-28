package com.backend.devfordev.controller;

import com.backend.devfordev.apiPayload.ApiResponse;
import com.backend.devfordev.apiPayload.code.status.SuccessStatus;
import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.dto.SignUpRequest;
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
    public ResponseEntity<ApiResponse> createCommunity(@RequestBody CommunityRequest.CommunityCreateRequest request,  @AuthenticationPrincipal User user){

        ApiResponse apiResponse = ApiResponse.builder()
                .result(communityService.createCommunity(request, Long.parseLong(user.getUsername())))
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

//        SignUpResponse member = memberService.registerMember(request);
//        return ApiResponse.onSuccess(MemberConverter.toSignUpResponse(member));
    }


    @Operation(summary = "커뮤니티 글 전체 조회")
    @GetMapping(value = "/v1/community")
    public ResponseEntity<ApiResponse> getCommunityList(
            @RequestParam(required = false) CommunityCategory category,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false, defaultValue = "recent") String sortBy
            ) {
        // 카테고리가 있을 경우 서비스에 Optional로 전달
        List<CommunityResponse.CommunityListResponse> communityList = communityService.getCommunityList(
                Optional.ofNullable(category),
                Optional.ofNullable(searchTerm),
                sortBy);

        ApiResponse apiResponse = ApiResponse.builder()
                .result(communityList)
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

//        SignUpResponse member = memberService.registerMember(request);
//        return ApiResponse.onSuccess(MemberConverter.toSignUpResponse(member));
    }


    @Operation(summary = "커뮤니티 글 상세 조회")
    @GetMapping(value = "/v1/community/{id}")
    public ResponseEntity<ApiResponse> getCommunityDetail(@PathVariable Long id) {
        CommunityResponse.CommunityDetailResponse communityDetail = communityService.getCommunityDetail(id);

        ApiResponse apiResponse = ApiResponse.builder()
                .result(communityDetail)
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }



    @Operation(summary = "인기 커뮤니티 Top 5 유저 조회 (좋아요 수 기준)")
    @GetMapping(value = "/v1/community/top5")
    public ResponseEntity<ApiResponse> getTop5UsersByTotalLikes() {
        // 인기 Top 5 유저 조회
        List<CommunityResponse.CommunityTop5Response> top5Users = communityService.getTop5UsersByTotalLikes();


        ApiResponse apiResponse = ApiResponse.builder()
                .result(top5Users)
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "커뮤니티 글 삭제")
    @DeleteMapping(value = "/v1/community/{id}")
    public ResponseEntity<ApiResponse> deleteCommunity(@PathVariable Long id, @AuthenticationPrincipal User user) {
        communityService.deleteCommunity(id, Long.parseLong(user.getUsername()));

        ApiResponse apiResponse = ApiResponse.builder()
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @Operation(summary = "커뮤니티 글 수정")
    @PatchMapping(value = "/v1/community/{id}")
    public ResponseEntity<ApiResponse> updateCommunity(@RequestBody CommunityRequest.CommunityUpdateRequest request, @PathVariable Long id, @AuthenticationPrincipal User user) {

        ApiResponse apiResponse = ApiResponse.builder()
                .result(communityService.updateCommunity(id, request, Long.parseLong(user.getUsername())))
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
