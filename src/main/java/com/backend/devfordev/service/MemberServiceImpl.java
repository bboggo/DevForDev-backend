package com.backend.devfordev.service;

import com.backend.devfordev.converter.MemberConverter;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.dto.SignUpRequest;
import com.backend.devfordev.dto.SignUpResponse;
import com.backend.devfordev.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Transactional
    public SignUpResponse registerMember(SignUpRequest request) {
        Member member = MemberConverter.toMember(request);
        member = memberRepository.save(member);
        return MemberConverter.toSignUpResponse(member);
    }
}
