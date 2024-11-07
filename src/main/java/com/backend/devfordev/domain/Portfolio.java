package com.backend.devfordev.domain;


import com.backend.devfordev.domain.enums.CommunityCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "portfolio")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Portfolio extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "port_id")
    private Long id;

    @Column(name = "port_title", nullable = false)
    private String portTitle;

    @Column(name = "port_content", nullable = false)
    private String portContent;

    @Column(name = "port_image", nullable = false)
    private String portImageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
