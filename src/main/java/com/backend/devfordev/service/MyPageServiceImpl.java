package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.converter.MyPageConverter;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.MemberInfo;
import com.backend.devfordev.dto.MyPageInfoResponse;
import com.backend.devfordev.repository.MemberInfoRepository;
import com.backend.devfordev.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyPageServiceImpl implements MyPageService{
    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;
    @Override
    @Transactional
    public MyPageInfoResponse.ProfileResponse getProfile(Long userId) {
        // Member 및 MemberInfo 조회
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        MemberInfo memberInfo = memberInfoRepository.findByMember(member);

        // 컨버터를 통해 DTO 변환
        return MyPageConverter.toGetProfileResponse(member, memberInfo);
    }
}