package com.backend.devfordev.domain;

import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.domain.enums.ProjectCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Project {

    /*
    제목
    개발(프로젝트) 분야
    링크
    내용
    태그(10개)
    대표 이미지 등록
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private Long id;

    @Column(name = "pro_title", nullable = false)
    private String projectTitle;

    @Column(name = "pro_content", columnDefinition = "TEXT", nullable = false)
    private String projectContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "pro_category", nullable = false)
    private ProjectCategory projectCategory;

    @Column(name = "pro_image")
    private String projectImageUrl;

    @Column(name = "pro_tags")
    private String projectTags;

    @Column(name = "pro_views", nullable = false)
    private Long projectViews = 0L;  // 기본값 설정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public void setTags(List<String> tags) {
        this.projectTags = String.join(",", tags);
    }
}
