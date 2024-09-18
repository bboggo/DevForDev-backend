package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.ExceptionHandler;
import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.GeneralException;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.converter.MemberConverter;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.MemberRefreshToken;
import com.backend.devfordev.dto.SignInRequest;
import com.backend.devfordev.dto.SignInResponse;
import com.backend.devfordev.dto.SignUpRequest;
import com.backend.devfordev.dto.SignUpResponse;
import com.backend.devfordev.repository.MemberRefreshTokenRepository;
import com.backend.devfordev.repository.MemberRepository;
import com.backend.devfordev.security.TokenProvider;
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

    // 회원가입
    // 이미지 추가
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        Member member = MemberConverter.toMember(request, encoder);
        if(memberRepository.findByName(member.getName()).isPresent()) {
            throw new ExceptionHandler(ErrorStatus.DUPLICATED_NAME);
        }

        if(memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new ExceptionHandler(ErrorStatus.DUPLICATED_EMAIL);
        }

        member = memberRepository.save(member);
        return MemberConverter.toSignUpResponse(member);
    }

    // 로그인
    @Transactional
    public SignInResponse signIn(SignInRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .filter(it -> encoder.matches(request.password(), it.getPassword()))
                .orElseThrow(() -> new MemberHandler(ErrorStatus.EXPIRED_JWT_ERROR));
        String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", member.getId(), member.getName()));
        String refreshToken = tokenProvider.createRefreshToken();

        memberRefreshTokenRepository.findById(member.getId())
                .ifPresentOrElse(
                        it -> it.updateRefreshToken(refreshToken),
                        ()-> memberRefreshTokenRepository.save(new MemberRefreshToken(member, refreshToken))
                ) ;
        return MemberConverter.toSignInResponse(member, accessToken, refreshToken);

    }


}
