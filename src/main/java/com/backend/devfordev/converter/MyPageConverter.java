package com.backend.devfordev.converter;

import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.MemberInfo;
import com.backend.devfordev.domain.enums.Affiliation;
import com.backend.devfordev.dto.MyPageInfoRequest;
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

    public static void toUpdateProfileRequest(
            Member member,
            MemberInfo memberInfo,
            MyPageInfoRequest.ProfileUpdateRequest request) {
        // Member 업데이트
        if (request.getGitHub() != null) {
            member.setGithub(request.getGitHub());
        }

        // MemberInfo 업데이트
        if (request.getNickname() != null) {
            memberInfo.setNickname(request.getNickname());
        }

        if (request.getImageUrl() != null) {
            memberInfo.setImageUrl(request.getImageUrl());
        }

        if (request.getIntroduction() != null) {
            memberInfo.setIntroduction(request.getIntroduction());
        }

        if (request.getPosition() != null) {
            memberInfo.setPosition(request.getPosition());
        }

        if (request.getTechStacks() != null && !request.getTechStacks().isEmpty()) {
            // 기술 스택 리스트를 쉼표로 구분된 문자열로 저장
            memberInfo.setTechStacks(String.join(",", request.getTechStacks()));
        }

        if (request.getAffiliation() != null) {
            memberInfo.setAffiliation(request.getAffiliation());
        }

        // 완성률은 자동 계산 (예: 프로필 필드 중 입력된 비율로 계산)
        calculateCompletionRate(memberInfo);


    }
    private static void calculateCompletionRate(MemberInfo memberInfo) {
        int totalFields = 6; // 업데이트 가능한 필드 수 (닉네임, 이미지, 소개, 포지션, 기술 스택, 소속)
        int filledFields = 0;

        if (memberInfo.getNickname() != null && !memberInfo.getNickname().isEmpty()) filledFields++;
        if (memberInfo.getImageUrl() != null && !memberInfo.getImageUrl().isEmpty()) filledFields++;
        if (memberInfo.getIntroduction() != null && !memberInfo.getIntroduction().isEmpty()) filledFields++;
        if (memberInfo.getPosition() != null && !memberInfo.getPosition().isEmpty()) filledFields++;
        if (memberInfo.getTechStacks() != null && !memberInfo.getTechStacks().isEmpty()) filledFields++;
        if (memberInfo.getAffiliation() != null) filledFields++;

        // 완성률 계산
        memberInfo.setCompletionRate((long) ((filledFields / (double) totalFields) * 100));
    }

    public static MyPageInfoResponse.ProfileUpdateResponse ProfileUpdateResponse(Member member, MemberInfo memberInfo) {
        return new MyPageInfoResponse.ProfileUpdateResponse(
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


