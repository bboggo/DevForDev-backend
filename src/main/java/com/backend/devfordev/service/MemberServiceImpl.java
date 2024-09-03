package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.ExceptionHandler;
import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.GeneralException;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.converter.MemberConverter;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.dto.SignUpRequest;
import com.backend.devfordev.dto.SignUpResponse;
import com.backend.devfordev.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    // 닉네임 중복
    // 이메일 중복
    // 이미지
    @Transactional
    public SignUpResponse registMember(SignUpRequest request) {
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
}
