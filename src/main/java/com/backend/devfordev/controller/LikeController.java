package com.backend.devfordev.controller;

import com.backend.devfordev.apiPayload.ApiResponse;
import com.backend.devfordev.apiPayload.code.status.SuccessStatus;
import com.backend.devfordev.domain.Heart;
import com.backend.devfordev.dto.LikeRequest;
import com.backend.devfordev.dto.LikeResponse;
import com.backend.devfordev.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "좋아요")
@RequiredArgsConstructor
@RestController
@RequestMapping
@Slf4j
public class LikeController {
        private final LikeService likeService;

        // 좋아요 추가
        @Operation(summary = "좋아요")
        @PostMapping(value = "/v1/likes")
        public ResponseEntity<ApiResponse> addLike(@RequestBody LikeRequest request, @AuthenticationPrincipal User user) {

            System.out.println("!!!!!!!!!!!!!!!!!!!");
            System.out.println(request.getLikeId());
            System.out.println(request.getLikeType());
            log.info("LikeRequest: {}", request);
            log.info("likeId: {}", request.getLikeId());
            log.info("Authenticated User ID: {}", user.getUsername());

            // 좋아요 생성
            LikeResponse likeResponse = likeService.createLike(request, Long.parseLong(user.getUsername()));  // LikeResponse로 수정

            ApiResponse apiResponse = ApiResponse.builder()
                    .result(likeResponse)  // LikeResponse로 수정
                    .isSuccess(SuccessStatus._OK.getReason().getIsSuccess())
                    .code(SuccessStatus._OK.getCode())
                    .message(SuccessStatus._OK.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }

}
