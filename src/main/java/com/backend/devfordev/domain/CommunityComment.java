package com.backend.devfordev.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "community_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class CommunityComment extends BaseEntity{
    //댓글 id, 커뮤니티 id, 유저 id, 댓글 내용, 댓글 길이, 부모 댓글 id, 인공지능 여부
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "com_id")
    private Community community;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "is_ai_comment")
    private Boolean isAiComment;

}
