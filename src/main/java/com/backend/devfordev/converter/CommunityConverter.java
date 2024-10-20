package com.backend.devfordev.converter;

import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;

import java.time.LocalDateTime;

public class CommunityConverter {
    public static Community toCommunity(CommunityRequest.CommunityCreateRequest request, Member member) {
        return Community.builder()
                .communityCategory(CommunityCategory.valueOf(request.getCommunityCategory()))
                .communityTitle(request.getCommunityTitle())
                .communityContent(request.getCommunityContent())
                .communityAI(request.getCommunityAI())
                .member(member)
                .communityViews(0L)
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

    public static CommunityResponse.CommunityListResponse toCommunityListResponse(Community community, CommunityResponse.MemberInfo member, Long likeCount, String shortenedContent) {
        return new CommunityResponse.CommunityListResponse(
                community.getId(),
                community.getCommunityCategory(),
                community.getCommunityTitle(),
                shortenedContent,
                member,
                community.getCreatedAt(),
                0L,
                community.getCommunityViews(),
                likeCount
        );

    }

    public static CommunityResponse.CommunityDetailResponse toCommunityLDetailResponse(Community community, CommunityResponse.MemberInfo member, Long likeCount) {
        return new CommunityResponse.CommunityDetailResponse(
                community.getId(),
                community.getCommunityCategory(),
                community.getCommunityTitle(),
                community.getCommunityContent(),
                member,
                community.getCreatedAt(),
                0L,
                community.getCommunityViews(),
                likeCount
        );

    }

    public static CommunityResponse.CommunityTop5Response toCommunityTop5Response(CommunityResponse.MemberInfo member, Long totalLikes) {
        return new CommunityResponse.CommunityTop5Response(
                member,
                totalLikes
        );
    }
}
