package com.backend.devfordev.service;

import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;

import java.util.List;

public interface CommunityService {
    public CommunityResponse.CommunityCreateResponse createCommunity(CommunityRequest.CommunityCreateRequest request, Long userId);
    public List<CommunityResponse.CommunityListResponse> getCommunityList();
}
