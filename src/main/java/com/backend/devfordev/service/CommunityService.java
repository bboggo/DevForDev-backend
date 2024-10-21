package com.backend.devfordev.service;

import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommunityService {
    public CommunityResponse.CommunityCreateResponse createCommunity(CommunityRequest.CommunityCreateRequest request, Long userId);
    public List<CommunityResponse.CommunityListResponse> getCommunityList(Optional<CommunityCategory> categoryOpt,  Optional<String> searchTermOpt, String sortBy);

    public CommunityResponse.CommunityDetailResponse getCommunityDetail(Long id);
    public List<CommunityResponse.CommunityTop5Response> getTop5UsersByTotalLikes();


    public void deleteCommunity(Long id, Long userId);
}
