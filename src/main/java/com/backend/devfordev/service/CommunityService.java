package com.backend.devfordev.service;

import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;

public interface CommunityService {
    public CommunityResponse.CommunityCreateResponse createCommunity(CommunityRequest.CommunityCreateRequest request);
}
