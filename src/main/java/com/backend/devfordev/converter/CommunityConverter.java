package com.backend.devfordev.converter;

import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;

import java.time.LocalDateTime;

public class CommunityConverter {
    public static Community toCommunity(CommunityRequest.CommunityCreateRequest request, Member member, CommunityCategory category) {

        return Community.builder()
                .communityCategory(category)
                .communityTitle(request.getCommunityTitle())
                .communityContent(request.getCommunityContent())
                .isComment(request.getIsComment())
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
                community.getIsComment(),
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
                community.getIsComment(),
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

    public static Community toUpdateCommunity(CommunityRequest.CommunityUpdateRequest request) {
        return Community.builder()
                .communityCategory(CommunityCategory.valueOf(request.getCommunityCategory()))
                .communityTitle(request.getCommunityTitle())
                .communityContent(request.getCommunityContent())
                .build();
    }


    public static CommunityResponse.CommunityUpdateResponse toCommunityUpdateResponse(Community community) {
        return new CommunityResponse.CommunityUpdateResponse(
                community.getId(),
                community.getCommunityCategory(),
                community.getCommunityTitle(),
                community.getCommunityContent(),
                community.getMember().getId(),
                community.getIsComment(),
                community.getCreatedAt(),
                community.getModifiedAt()
        );
    }
}
