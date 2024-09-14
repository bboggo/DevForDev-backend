package com.backend.devfordev.converter;

import com.backend.devfordev.domain.Member;
import com.backend.devfordev.dto.SignInResponse;
import com.backend.devfordev.dto.SignUpRequest;
import com.backend.devfordev.dto.SignUpResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberConverter {
    public static Member toMember(SignUpRequest signUpRequest, PasswordEncoder encoder) {
        String imageUrl = "domain.com";
        return Member.builder()
                .email(signUpRequest.email())
                .password(encoder.encode(signUpRequest.password()))
                .name(signUpRequest.name())
                .imageUrl(imageUrl)
                .birth(signUpRequest.birth())
                .major(signUpRequest.major())
                .build();
    }

    public static SignUpResponse toSignUpResponse(Member member) {
        return new SignUpResponse(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getImageUrl(),
                member.getMajor(),
                member.getBirth()
        );
    }

    public static SignInResponse toSignInResponse(Member member, String accessToken, String refreshToken) {
        return new SignInResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getImageUrl(),
                accessToken,
                refreshToken
        );
    }
}

