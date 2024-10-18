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

    public static LikeResponse toLikeResponse(Heart heart, Long likes, Long memberId, int likeStatus) {
        if (heart == null) {
            // 좋아요 취소 상태 (-1)
            return LikeResponse.builder()
                    .memberId(memberId)
                    .likeId(null)
                    .likeType(null)
                    .likes(likes)
                    .likeStatus(-1)  // 취소 상태
                    .build();
        }

        // 좋아요 추가 상태 (+1)
        return LikeResponse.builder()
                .memberId(memberId)
                .likeId(heart.getLikeId())
                .likeType(heart.getLikeType())
                .likes(likes)
                .likeStatus(likeStatus)  // +1 or -1에 따라 처리
                .build();
    }
}