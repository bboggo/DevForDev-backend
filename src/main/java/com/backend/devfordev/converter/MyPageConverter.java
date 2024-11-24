package com.backend.devfordev.converter;

import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.MemberInfo;
import com.backend.devfordev.domain.enums.Affiliation;
import com.backend.devfordev.dto.MyPageInfoResponse;
import com.backend.devfordev.dto.SignUpResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.Arrays;

public class MyPageConverter {
    public static MyPageInfoResponse.ProfileResponse toGetProfileResponse(Member member, MemberInfo memberInfo) {
        return new MyPageInfoResponse.ProfileResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                memberInfo.getNickname(),
                memberInfo.getImageUrl(),
                memberInfo.getIntroduction(),
                member.getGithub(),
                memberInfo.getPosition(),
                memberInfo.getTechStacks() != null ? Arrays.asList(memberInfo.getTechStacks().split(",")) : new ArrayList<>(),
                memberInfo.getAffiliation(),
                memberInfo.getCompletionRate()
        );
    }
}
