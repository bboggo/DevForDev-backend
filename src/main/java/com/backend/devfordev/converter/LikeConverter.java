package com.backend.devfordev.converter;

import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.Heart;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.enums.LikeType;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.dto.LikeRequest;
import com.backend.devfordev.dto.LikeResponse;
import io.swagger.v3.oas.annotations.media.Schema;


public class LikeConverter {

    public static Heart toLike(LikeRequest request, Member member) {
        return Heart.builder()
                .member(member)  // 로그인한 유저 정보
                .likeId(request.getLikeId())
                .likeType(LikeType.valueOf(request.getLikeType()))  // LikeType 설정
                .build();
    }

    public static LikeResponse toLikeResponse(Heart heart, Long likes, Long memberId) {
        return new LikeResponse(
                memberId,
                heart.getLikeId(),
                heart.getLikeType(),
                likes
        );
    }

}