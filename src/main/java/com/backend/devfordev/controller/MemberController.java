package com.backend.devfordev.controller;

import com.backend.devfordev.apiPayload.ApiResponse;
import com.backend.devfordev.apiPayload.code.status.SuccessStatus;
import com.backend.devfordev.dto.SignInRequest;
import com.backend.devfordev.dto.SignUpRequest;
import com.backend.devfordev.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 가입 및 로그인 API")
@RequiredArgsConstructor
@RestController
@RequestMapping
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 가입")
    @PostMapping(value = "/v1/auth/sign-up")
    public ResponseEntity<ApiResponse> createMember(@RequestBody @Valid SignUpRequest request){

            ApiResponse apiResponse = ApiResponse.builder()
                    .result(memberService.signUp(request))
                    .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                    .code(SuccessStatus._OK.getCode())
                    .message(SuccessStatus._OK.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

//        SignUpResponse member = memberService.registerMember(request);
//        return ApiResponse.onSuccess(MemberConverter.toSignUpResponse(member));
    }

    @Operation(summary = "로그인")
    @PostMapping(value = "/v1/auth/sign-in")
    public ResponseEntity<ApiResponse> signIn(@Valid @RequestBody SignInRequest request) {
        ApiResponse ar = ApiResponse.builder()
                .result(memberService.signIn(request))
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(ar);
    }

    @Operation(summary = "로그인한 유저 조회")
    @GetMapping("v1/auth")
    public ResponseEntity<ApiResponse> getMemberInfo(@AuthenticationPrincipal User user) {
        ApiResponse ar = ApiResponse.builder()
                .result(memberService.getMember(Long.parseLong(user.getUsername())))
                .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(ar);
    }

}
