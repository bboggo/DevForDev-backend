package com.backend.devfordev.converter;

import com.backend.devfordev.domain.Member;
import com.backend.devfordev.dto.SignUpRequest;

public class MemberConverter {
    public static Member toMember(SignUpRequest signUpRequest) {
        String imageUrl = "domain.com";
        return Member.builder()
                .email(signUpRequest.email())
                .password(signUpRequest.password())
                .name(signUpRequest.name())
                .imageUrl(imageUrl)
                .birth(signUpRequest.birth())
                .build();
    }
}
