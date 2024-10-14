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
    public List<CommunityResponse.CommunityListResponse> getCommunityList(Optional<CommunityCategory> categoryOpt,  Optional<String> searchTermOpt) {
        List<Community> communities = communityRepository.findAll().stream()
                .filter(community -> {
                    // 카테고리 필터링
                    return categoryOpt.map(communityCategory -> community.getCommunityCategory() == communityCategory).orElse(true);
                })
                .filter(community -> {
                    // 검색 필터링: 제목, 작성자 이름, 내용 중 하나라도 일치하면 true 반환
                    if (searchTermOpt.isPresent()) {
                        String searchTerm = searchTermOpt.get().toLowerCase();
                        return community.getCommunityTitle().toLowerCase().contains(searchTerm) ||
                                community.getMember().getName().toLowerCase().contains(searchTerm) ||
                                community.getCommunityContent().toLowerCase().contains(searchTerm);
                    }
                    return true;
                })
                .toList();

        // DTO 변환
        return communities.stream()
                .map(community -> {
                    CommunityResponse.MemberInfo memberInfo = new CommunityResponse.MemberInfo(
                            community.getMember().getId(),
                            community.getMember().getImageUrl(),
                            community.getMember().getName()
                    );


                    return CommunityConverter.toCommunityListResponse(community, memberInfo);
                })
                .collect(Collectors.toList());
    }
}
