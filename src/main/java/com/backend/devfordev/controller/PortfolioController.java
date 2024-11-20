package com.backend.devfordev.controller;


import com.backend.devfordev.apiPayload.ApiResponse;
import com.backend.devfordev.apiPayload.code.status.SuccessStatus;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.PortfolioRequest;
import com.backend.devfordev.dto.PortfolioResponse;
import com.backend.devfordev.service.PortfolioService;
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

@Tag(name = "포트폴리오 API")
@RequiredArgsConstructor
@RestController
@RequestMapping
@Slf4j
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Operation(summary = "포트폴리오 글 등록")
    @PostMapping(value = "/v1/portfolio")
    public ResponseEntity<ApiResponse<PortfolioResponse.PortCreateResponse>> createPortfolio(@RequestBody PortfolioRequest.PortfolioCreateRequest request, @AuthenticationPrincipal User user) {

        PortfolioResponse.PortCreateResponse portCreateResponse = portfolioService.createPortfolio(request, Long.parseLong(user.getUsername()));
        ApiResponse<PortfolioResponse.PortCreateResponse> apiResponse = ApiResponse.onSuccess(portCreateResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
