package com.backend.devfordev.converter;

import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;

public class CommunityConverter {
    public static Community toCommunity(CommunityRequest.CommunityCreateRequest request, Member member) {
        return Community.builder()
                .communityCategory(CommunityCategory.valueOf(request.getCommunityCategory()))
                .communityTitle(request.getCommunityTitle())
                .communityContent(request.getCommunityContent())
                .communityAI(request.getCommunityAI())
                .member(member)
                .build();
    }


    public static CommunityResponse.CommunityCreateResponse toCommunityResponse(Community community) {
        return new CommunityResponse.CommunityCreateResponse(
                community.getId(),
                community.getCommunityCategory(),
                community.getCommunityTitle(),
                community.getCommunityContent(),
                community.getMember().getId(),
                community.getCommunityAI(),
                community.getCreatedAt()

        );
    }

    public static CommunityResponse.CommunityListResponse toCommunityListResponse(Community community, CommunityResponse.MemberInfo member) {
        return new CommunityResponse.CommunityListResponse(
                community.getId(),
                community.getCommunityCategory(),
                community.getCommunityTitle(),
                community.getCommunityContent(),
                member,
                community.getCreatedAt(),
                0L,
                0L,
                0L
        );

    }
}


