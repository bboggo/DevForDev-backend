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
import com.backend.devfordev.repository.LikeRepository;
import com.backend.devfordev.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityServiceImpl implements CommunityService{
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
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
    public List<CommunityResponse.CommunityListResponse> getCommunityList(Optional<CommunityCategory> categoryOpt, Optional<String> searchTermOpt) {
        // 커뮤니티와 좋아요 수를 한 번의 쿼리로 가져옴
        List<Object[]> results = communityRepository.findAllWithLikesAndMember();

        // 필터링 및 DTO 변환
        return results.stream()
                .map(result -> {
                    Community community = (Community) result[0];  // 첫 번째는 Community 객체
                    Long likeCount = (Long) result[1];            // 두 번째는 좋아요 수

                    // 카테고리 필터링
                    if (categoryOpt.isPresent() && !community.getCommunityCategory().equals(categoryOpt.get())) {
                        return null;
                    }

                    // 검색 필터링: 제목, 작성자 이름, 내용 중 하나라도 일치하면 true 반환
                    if (searchTermOpt.isPresent()) {
                        String searchTerm = searchTermOpt.get().toLowerCase();
                        boolean matches = community.getCommunityTitle().toLowerCase().contains(searchTerm) ||
                                community.getMember().getName().toLowerCase().contains(searchTerm) ||
                                community.getCommunityContent().toLowerCase().contains(searchTerm);
                        if (!matches) {
                            return null;
                        }
                    }

                    // MemberInfo 생성
                    CommunityResponse.MemberInfo memberInfo = new CommunityResponse.MemberInfo(
                            community.getMember().getId(),
                            community.getMember().getImageUrl(),
                            community.getMember().getName()
                    );

                    // DTO 변환
                    return CommunityConverter.toCommunityListResponse(community, memberInfo, likeCount);
                })
                .filter(Objects::nonNull)  // 필터링에서 null이 반환된 경우 제거
                .collect(Collectors.toList());
    }

}
