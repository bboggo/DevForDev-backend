package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.ExceptionHandler;
import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.CommunityHandler;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.Comparator;
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
        // 필요없나,,?
        if(!"CAREER".equals(category) && !"OTHER".equals(category) && !"SKILL".equals(category)) {
            throw new CommunityHandler(ErrorStatus.INVALID_CATEGORY);
        }
        communityRepository.save(community);

        return CommunityConverter.toCommunityResponse(community);
     }



    @Override
    @Transactional
    public List<CommunityResponse.CommunityListResponse> getCommunityList(Optional<CommunityCategory> categoryOpt, Optional<String> searchTermOpt, String sortBy) {
        // 커뮤니티와 좋아요 수를 한 번의 쿼리로 가져옴
        List<Object[]> results = communityRepository.findAllWithLikesAndMember();

        // 필터링 및 DTO 변환
        List<CommunityResponse.CommunityListResponse> communityList = results.stream()
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

                    // communityContent를 80자까지만 잘라서 보여줌
                    String shortenedContent = community.getCommunityContent();
                    if (shortenedContent.length() > 80) {
                        shortenedContent = shortenedContent.substring(0, 80) + "...";  // 80자까지만 자르고 "..." 추가
                    }

                    // DTO 변환
                    return CommunityConverter.toCommunityListResponse(community, memberInfo, likeCount, shortenedContent);
                })
                .filter(Objects::nonNull)  // 필터링에서 null이 반환된 경우 제거
                .collect(Collectors.toList());

        // 정렬 기준에 따른 정렬
        switch (sortBy.toLowerCase()) {
            case "likes":   // 좋아요 순 정렬
                communityList.sort(Comparator.comparingLong(CommunityResponse.CommunityListResponse::getLikes).reversed());
                break;
            case "views":   // 조회수 순 정렬
                communityList.sort(Comparator.comparingLong(CommunityResponse.CommunityListResponse::getViews).reversed());
                break;
            case "recent":  // 최신순 정렬 (기본)
            default:
                communityList.sort(Comparator.comparing(CommunityResponse.CommunityListResponse::getCreatedAt).reversed());
                break;
        }

        return communityList;
    }

    @Override
    @Transactional
    public CommunityResponse.CommunityDetailResponse getCommunityDetail(Long id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new CommunityHandler(ErrorStatus.COMMUNITY_NOT_FOUND));

        Long Likecount = likeRepository.countByCommunityId(id);
        CommunityResponse.MemberInfo memberInfo = new CommunityResponse.MemberInfo(
                community.getMember().getId(),
                community.getMember().getImageUrl(),
                community.getMember().getName()
        );

        return CommunityConverter.toCommunityLDetailResponse(community, memberInfo, Likecount);
    }



    @Transactional
    public List<CommunityResponse.CommunityTop5Response> getTop5UsersByTotalLikes() {
        // 유저별 총 좋아요 수 계산
        List<Object[]> totalLikesForUsers = communityRepository.findTop5UsersByTotalLikes();

        // 로그로 각 유저의 총 좋아요 수 출력
        totalLikesForUsers.forEach(result -> {
            Member member = (Member) result[0];
            Long totalLikes = (Long) result[1];
            System.out.println(member.getId());
            //log.info("Member ID: {}, Name: {}, Total Likes: {}", member.getId(), member.getName(), totalLikes);
        });

        // 좋아요 수 기준으로 상위 5명의 유저를 가져옴
        return totalLikesForUsers.stream()
                .limit(5)  // 상위 5명 가져오기
                .map(result -> {
                    Member member = (Member) result[0];  // 유저 객체
                    Long totalLikes = (Long) result[1];  // 총 좋아요 수

                    // MemberInfo 생성
                    CommunityResponse.MemberInfo memberInfo = new CommunityResponse.MemberInfo(
                            member.getId(),
                            member.getImageUrl(),
                            member.getName()
                    );

                    // CommunityTop5Response로 변환
                    return CommunityConverter.toCommunityTop5Response(memberInfo, totalLikes);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCommunity(Long id, Long userId) {


        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new CommunityHandler(ErrorStatus.COMMUNITY_NOT_FOUND));

        // 글 작성자와 로그인한 유저가 동일한지 확인
        if (!community.getMember().getId().equals(userId)) {
            throw new CommunityHandler(ErrorStatus.UNAUTHORIZED_USER); // 예외 처리 (권한 없음)
        }

        community.deleteSoftly(); // BaseEntity에서 상속받은 softDelete 메소드 호출

        communityRepository.save(community); // 변경된 엔티티 저장
    }
}
