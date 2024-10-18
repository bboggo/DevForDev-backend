package com.backend.devfordev.service;


import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.CommunityHandler;
import com.backend.devfordev.apiPayload.exception.handler.LikeHandler;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.converter.CommunityConverter;
import com.backend.devfordev.converter.LikeConverter;
import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.Heart;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.enums.LikeType;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.dto.LikeRequest;
import com.backend.devfordev.dto.LikeResponse;
import com.backend.devfordev.repository.LikeRepository;
import com.backend.devfordev.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public LikeResponse createLike(LikeRequest request, Long userId) {

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_MEMBER));
        LikeType likeType;
        try {
            // String 값을 ENUM으로 변환
            likeType = LikeType.valueOf(request.getLikeType().toUpperCase());
        } catch (IllegalArgumentException ex) {

            throw new LikeHandler(ErrorStatus.INVALID_LIKE_TYPE);
        }

        Heart heart = LikeConverter.toLike(request, member);
        likeRepository.save(heart);

        // Heart 객체 저장
        return LikeConverter.toLikeResponse(heart, 0L, userId);// 저장 후 반환
    }
}

