package com.backend.devfordev.domain;

import com.backend.devfordev.domain.enums.CommunityCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "community")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Community extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "com_category", nullable = false)
    private CommunityCategory communityCategory;


    @Column(name = "com_title", nullable = false)
    private String communityTitle;

    @Column(name = "com_content", nullable = false)
    private String communityContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Member member;

}
