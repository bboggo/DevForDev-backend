package com.backend.devfordev.service;

import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.dto.PortfolioRequest;
import com.backend.devfordev.dto.PortfolioResponse;

import java.util.List;
import java.util.Optional;

public interface PortfolioService {
    public PortfolioResponse.PortCreateResponse createPortfolio(PortfolioRequest.PortfolioCreateRequest request, Long userId);
    public List<PortfolioResponse.PortfolioListResponse> getPortList(String position, Optional<String> searchTermOpt, String sortBy);
}
