package com.backend.devfordev.service;

import com.backend.devfordev.converter.CommunityConverter;
import com.backend.devfordev.domain.Community;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommunityServiceImpl implements CommunityService{
    private final CommunityRepository communityRepository;

    @Override
    @Transactional
    public CommunityResponse.CommunityCreateResponse createCommunity(CommunityRequest.CommunityCreateRequest request) {
        Community community = CommunityConverter.toCommunity(request);
        communityRepository.save(community);

        return CommunityConverter.toCommunityResponse(community);
     }


}
