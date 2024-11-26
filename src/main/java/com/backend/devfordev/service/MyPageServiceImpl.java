package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.ExceptionHandler;
import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.converter.MemberConverter;
import com.backend.devfordev.converter.MyPageConverter;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.MemberInfo;
import com.backend.devfordev.dto.MyPageInfoRequest;
import com.backend.devfordev.dto.MyPageInfoResponse;
import com.backend.devfordev.dto.SignUpRequest;
import com.backend.devfordev.dto.SignUpResponse;
import com.backend.devfordev.repository.MemberInfoRepository;
import com.backend.devfordev.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyPageServiceImpl implements MyPageService{
    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final PasswordEncoder encoder;
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

    @Transactional
    public MyPageInfoResponse.ProfileUpdateResponse updateProfile(Long memberId, MyPageInfoRequest.ProfileUpdateRequest request) {
        // Member와 MemberInfo 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        MemberInfo memberInfo = memberInfoRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("MemberInfo not found"));

        // 업데이트 수행
        MyPageConverter.toUpdateProfileRequest(member, memberInfo, request);

        // 변경된 엔티티 저장
        memberRepository.save(member);
        memberInfoRepository.save(memberInfo);
        return MyPageConverter.ProfileUpdateResponse(member, memberInfo);
    }


    @Override
    @Transactional
    public void updatePassword(Long memberId, MyPageInfoRequest.PasswordUpdateRequest passwordUpdateRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        // 기존 비밀번호와 새 비밀번호가 같은지 확인
        if (encoder.matches(passwordUpdateRequest.getPassword(), member.getPassword())) {
            throw new MemberHandler(ErrorStatus.SAME_PASSWORD_NOT_ALLOWED);
        }

        MyPageConverter.PasswordUpdateRequest(member, passwordUpdateRequest, encoder);
    }
}