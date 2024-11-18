package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.ExceptionHandler;
import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.CommunityHandler;
import com.backend.devfordev.apiPayload.exception.handler.GeneralException;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.apiPayload.exception.handler.TeamHandler;
import com.backend.devfordev.converter.MemberConverter;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.MemberInfo;
import com.backend.devfordev.domain.MemberRefreshToken;
import com.backend.devfordev.domain.Team;
import com.backend.devfordev.dto.*;
import com.backend.devfordev.repository.MemberInfoRepository;
import com.backend.devfordev.repository.MemberRefreshTokenRepository;
import com.backend.devfordev.repository.MemberRepository;
import com.backend.devfordev.security.TokenProvider;
import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;
    private final MemberInfoRepository memberInfoRepository;

    // 회원가입
    // 이미지 추가
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {

            Member member = MemberConverter.toMember(request, encoder);

//            if(memberRepository.findByName(member.getName()).isPresent()) {
//                throw new ExceptionHandler(ErrorStatus.DUPLICATED_NAME);
//            }

            if(memberRepository.findByEmail(member.getEmail()).isPresent()) {
                throw new ExceptionHandler(ErrorStatus.DUPLICATED_EMAIL);
            }


            member = memberRepository.save(member);

            MemberInfo memberInfo = MemberConverter.toMemberInfo(request, member);
            memberInfo = memberInfoRepository.save(memberInfo); // memberInfoRepository는 MemberInfo의 Repository입니다.

        return MemberConverter.toSignUpResponse(member, memberInfo);

    }

    // 로그인
    @Transactional
    public SignInResponse signIn(SignInRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .filter(it -> encoder.matches(request.password(), it.getPassword()))
                .orElseThrow(() -> new MemberHandler(ErrorStatus.LOGIN_FAILED_PASSWORD_INCORRECT));

        MemberInfo memberInfo = memberInfoRepository.findByMember(member);


        String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", member.getId(), member.getName()));
        String refreshToken = tokenProvider.createRefreshToken();

        System.out.println(memberRefreshTokenRepository.findByMemberId(member.getId()) + "!!!!!!!!!!!!!!!!!!!!!!!!!");
        // 리프레시 토큰이 이미 있으면 토큰을 갱신하고 없으면 토큰 추가
        memberRefreshTokenRepository.findByMemberId(member.getId())
                .ifPresentOrElse(
                        it -> it.updateRefreshToken(refreshToken),
                        ()-> memberRefreshTokenRepository.save(new MemberRefreshToken(member, refreshToken))
                );
        return MemberConverter.toSignInResponse(member, memberInfo, accessToken, refreshToken);
    }

    @Transactional
    public MemberResponse getMember(Long userId) {
        // Member 및 MemberInfo 조회
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        MemberInfo memberInfo = memberInfoRepository.findByMember(member);

        // 컨버터를 통해 DTO 변환
        return MemberConverter.toGetMemberResponse(member, memberInfo);
    }
}
