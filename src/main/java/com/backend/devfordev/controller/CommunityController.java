package com.backend.devfordev.controller;

import com.backend.devfordev.apiPayload.ApiResponse;
import com.backend.devfordev.apiPayload.code.status.SuccessStatus;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.SignUpRequest;
import com.backend.devfordev.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "커뮤니티 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping
@Slf4j
public class CommunityController {
    private final CommunityService communityService;

    @Operation(summary = "커뮤니티 글 등록")
    @PostMapping(value = "/v1/community")
    public ResponseEntity<ApiResponse> createCommunity(@RequestBody CommunityRequest.CommunityCreateRequest request){

        ApiResponse apiResponse = ApiResponse.builder()
                .result(communityService.createCommunity(request))
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

//        SignUpResponse member = memberService.registerMember(request);
//        return ApiResponse.onSuccess(MemberConverter.toSignUpResponse(member));
    }
}
