package com.backend.devfordev.converter;

import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CommunityConverter {
    public static Community toCommunity(CommunityRequest.CommunityCreateRequest request) {
        return Community.builder()
                .communityCategory(CommunityCategory.valueOf(request.getCommunityCategory()))
                .communityTitle(request.getCommunityTitle())
                .communityContent(request.getCommunityContent())
                .build();
    }
}
