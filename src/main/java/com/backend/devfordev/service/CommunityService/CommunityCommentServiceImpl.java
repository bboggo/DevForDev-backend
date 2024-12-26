package com.backend.devfordev.service.CommunityService;

import com.backend.devfordev.converter.CommunityConverter;
import com.backend.devfordev.domain.CommunityEntity.Community;
import com.backend.devfordev.domain.CommunityEntity.CommunityComment;
import com.backend.devfordev.domain.MemberEntity.Member;
import com.backend.devfordev.dto.CommunityDto.CommunityCommentRequest;
import com.backend.devfordev.dto.CommunityDto.CommunityCommentResponse;
import com.backend.devfordev.repository.CommunityRepository.CommunityCommentRepository;
import com.backend.devfordev.repository.CommunityRepository.CommunityRepository;
import com.backend.devfordev.repository.MemberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityCommentServiceImpl implements CommunityCommentService{

    private final CommunityCommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CommunityCommentResponse addComment(Long comId, Long userId, CommunityCommentRequest request) {
        Community community = communityRepository.findById(comId)
                .orElseThrow(() -> new IllegalArgumentException("Community not found"));

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        CommunityComment parentComment = null;
        if (request.getParentId() != null) {
            parentComment = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
        }

        CommunityComment comment = CommunityComment.builder()
                .community(community)
                .member(member)
                .commentContent(request.getContent())
                .parent(parentComment)
                .build();


        CommunityComment savedComment = commentRepository.save(comment);


        return CommunityConverter.toCommunityCommentResponse(savedComment);
    }
}
