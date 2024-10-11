package com.backend.devfordev.dto;

import com.backend.devfordev.domain.Member;

public class CommunityResponse {
    public static class CommunityCreateResponse {
        Long id;
        String communityCategory;
        String communityTitle;
        String communityContent;
        Member member;
    }

}
