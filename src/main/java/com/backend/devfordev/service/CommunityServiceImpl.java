package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.ExceptionHandler;
import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.converter.CommunityConverter;
import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.repository.CommunityRepository;
import com.backend.devfordev.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityServiceImpl implements CommunityService{
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public CommunityResponse.CommunityCreateResponse createCommunity(CommunityRequest.CommunityCreateRequest request, Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_MEMBER));


        Community community = CommunityConverter.toCommunity(request, member);
        String category = request.getCommunityCategory();
        if(!"CAREER".equals(category) && !"OTHER".equals(category) && !"SKILL".equals(category)) {
            throw new ExceptionHandler(ErrorStatus.INVALID_CATEGORY);
        }
        communityRepository.save(community);

        return CommunityConverter.toCommunityResponse(community);
     }



    @Override
    @Transactional
    public List<CommunityResponse.CommunityListResponse> getCommunityList(Optional<CommunityCategory> categoryOpt) {
        List<Community> communities = categoryOpt
                .map(communityRepository::findByCommunityCategory)  // 카테고리가 있으면 필터링
                .orElseGet(communityRepository::findAll);

        // DTO 변환
        return communities.stream()
                .map(community -> {
                    CommunityResponse.MemberInfo memberInfo = new CommunityResponse.MemberInfo(
                            community.getMember().getId(),
                            community.getMember().getImageUrl()
                    );


                    return CommunityConverter.toCommunityListResponse(community, memberInfo);
                })
                .collect(Collectors.toList());
    }
}
