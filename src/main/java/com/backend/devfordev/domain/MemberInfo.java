package com.backend.devfordev.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class MemberInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_info_id")
    private Long id;

    @Column(name="nickname")
    private String nickname;

    @Column(name="image_url")
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
