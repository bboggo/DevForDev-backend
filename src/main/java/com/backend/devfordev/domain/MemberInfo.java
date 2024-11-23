package com.backend.devfordev.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder

/*
이름
이메일

(프로필)
프로필 사진
이름
닉네임
소개
깃허브

(커리어)
포지션
기술스택
소속
 */
public class MemberInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_info_id")
    private Long id;

    @Column(name="nickname")
    private String nickname;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="Introduction")
    private String introduction;

    @Column(name="position")
    private String position;

    @Column(name="tech_stacks")
    private String techStacks;

    @Column(name="affiliation")
    private String affiliation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
