package com.backend.devfordev.service;


import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.CommunityHandler;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.converter.CommunityConverter;
import com.backend.devfordev.converter.LikeConverter;
import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.Heart;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.dto.LikeRequest;
import com.backend.devfordev.dto.LikeResponse;
import com.backend.devfordev.repository.LikeRepository;
import com.backend.devfordev.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public LikeResponse createLike(LikeRequest request, Long userId) {
        // 사용자 ID로 Member 객체 조회
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_MEMBER));

        // LikeConverter를 통해 Heart 객체 생성
        Heart heart = LikeConverter.toLike(request, member);
        likeRepository.save(heart);

        // Heart 객체 저장
        return LikeConverter.toLikeResponse(heart, 0L, userId);// 저장 후 반환
    }
}

