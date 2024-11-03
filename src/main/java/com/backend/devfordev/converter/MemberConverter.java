package com.backend.devfordev.converter;

import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.MemberInfo;
import com.backend.devfordev.dto.SignInResponse;
import com.backend.devfordev.dto.SignUpRequest;
import com.backend.devfordev.dto.SignUpResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberConverter {
    public static Member toMember(SignUpRequest signUpRequest, PasswordEncoder encoder) {
        String imageUrl = "domain.com";
        String githubUrl = "https://github.com/" + signUpRequest.gitHub();  // 깃허브 URL 생성
        return Member.builder()
                .email(signUpRequest.email())
                .password(encoder.encode(signUpRequest.password()))
                .name(signUpRequest.name())
                .github(githubUrl)
                .build();
    }

    public static MemberInfo toMemberInfo(SignUpRequest signUpRequest, Member member) {
        String defaultImageUrl = "https://default-imageUrl.com";
        return MemberInfo.builder()
                .nickname(signUpRequest.name()) // 기본 닉네임은 name 필드로 설정
                .imageUrl(defaultImageUrl)
                .member(member)
                .build();
    }


    public static SignUpResponse toSignUpResponse(Member member, MemberInfo memberInfo) {
        return new SignUpResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getGithub(),
                memberInfo.getNickname(),
                memberInfo.getImageUrl()
        );
    }

    public static SignInResponse toSignInResponse(Member member,  MemberInfo memberInfo, String accessToken, String refreshToken) {
        return new SignInResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                memberInfo.getNickname(),
                memberInfo.getImageUrl(),
                accessToken,
                refreshToken
        );
    }
}

