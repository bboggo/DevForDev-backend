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
import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class MyPageServiceImpl implements MyPageService{
    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final PasswordEncoder encoder;
    private final S3Service s3Service;
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

    public MyPageInfoResponse.ProfileUpdateResponse updateProfile(Long memberId, MyPageInfoRequest.ProfileUpdateRequest request, MultipartFile profileImage) {
        try {
            // Member와 MemberInfo 엔티티 조회
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
            MemberInfo memberInfo = memberInfoRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("MemberInfo not found"));

            // 이미지 URL 처리
            String imageUrl;
            if (profileImage == null || profileImage.isEmpty()) {
                // 새 이미지가 없을 경우 기존 이미지 URL 유지
                imageUrl = memberInfo.getImageUrl() != null ? memberInfo.getImageUrl() : s3Service.saveDefaultProfileImage();
            } else {
                // 새 프로필 이미지 업로드
                imageUrl = s3Service.saveProfileImage(profileImage);
            }

            // 업데이트 수행 (이미지 URL 포함)
            MyPageConverter.toUpdateProfileRequest(member, memberInfo, request, imageUrl);

            // 변경된 엔티티 저장
            memberRepository.save(member);
            memberInfoRepository.save(memberInfo);

            // 업데이트 완료된 정보 반환
            return MyPageConverter.ProfileUpdateResponse(member, memberInfo);
        } catch (IOException e) {
            // 프로필 이미지 업로드 실패 예외 처리
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
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