package com.backend.devfordev.service;


import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.LikeHandler;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.converter.LikeConverter;
import com.backend.devfordev.domain.Heart;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.enums.LikeType;
import com.backend.devfordev.dto.LikeRequest;
import com.backend.devfordev.dto.LikeResponse;
import com.backend.devfordev.repository.LikeRepository;
import com.backend.devfordev.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public LikeResponse createLike(LikeRequest request, Long userId) {

        // Likes need to be calculated!!!!

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_MEMBER));
        LikeType likeType;
        try {
            // String 값을 ENUM으로 변환
            likeType = request.getLikeType();
        } catch (IllegalArgumentException ex) {

            throw new LikeHandler(ErrorStatus.INVALID_LIKE_TYPE);
        }
        // 해당 유저가 이미 해당 게시물에 좋아요를 눌렀는지 확인
        Optional<Heart> existingLike = likeRepository.findByMemberAndLikeIdAndLikeType(member, request.getLikeId(), likeType);


        if (existingLike.isPresent()) {
            // 이미 좋아요를 누른 상태면 좋아요 취소 (삭제)
            likeRepository.delete(existingLike.get());
            Long totalLikes = likeRepository.countByLikeIdAndLikeType(request.getLikeId(), likeType);
            return LikeConverter.toLikeResponse(null, totalLikes, userId);  // 좋아요 취소 상태 (-1)
        } else {
            // 좋아요를 누르지 않았다면 새로 추가
            Heart heart = LikeConverter.toLike(request, member);
            likeRepository.save(heart);
            Long totalLikes = likeRepository.countByLikeIdAndLikeType(request.getLikeId(), likeType);
            return LikeConverter.toLikeResponse(heart, totalLikes, userId);  // 좋아요 추가 상태 (+1)
        }

        // 전체 좋아요 수 계산
        //return LikeConverter.toLikeResponse(existingLike.orElse(null), totalLikes, userId, existingLike.isPresent() ? -1 : +1);
    }
}

