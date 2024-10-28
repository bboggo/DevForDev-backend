package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.CommunityHandler;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.apiPayload.exception.handler.TeamHandler;
import com.backend.devfordev.converter.CommunityConverter;
import com.backend.devfordev.converter.TeamConverter;
import com.backend.devfordev.domain.*;
import com.backend.devfordev.domain.enums.CommunityCategory;
import com.backend.devfordev.domain.enums.TeamType;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.dto.TeamRequest;
import com.backend.devfordev.dto.TeamResponse;
import com.backend.devfordev.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService{
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamTagRepository teamTagRepository;
    private final LikeRepository likeRepository;
    private final TeamTechStackRepository teamTechStackRepository;

    @Override
    @Transactional
    public TeamResponse.TeamCreateResponse createTeam(TeamRequest.TeamCreateRequest request, Long userId) {
        // 작성자 확인
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_MEMBER));

        if (request.getTeamTechStack().size() > 5) {
            throw new TeamHandler(ErrorStatus.INVALID_TECH_STACK_COUNT);
        }
        if (request.getTeamTags().size() > 5) {
            throw new TeamHandler(ErrorStatus.INVALID_TAG_COUNT);
        }



        List<TeamTag> tags = request.getTeamTags().stream()
                .map(tagName -> teamTagRepository.findByName(tagName)
                        .orElseGet(() -> teamTagRepository.save(TeamTag.builder().name(tagName).build())))
                .collect(Collectors.toList());

        Team team = TeamConverter.toTeam(request, member, tags);

        teamRepository.save(team);

        return TeamConverter.toTeamCreateResponse(team);

    }

    @Transactional
    @Override
    public void closeRecruitment(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamHandler(ErrorStatus.TEAM_NOT_FOUND));

        if (!team.getMember().getId().equals(userId)) {
            throw new MemberHandler(ErrorStatus.UNAUTHORIZED_USER);
        }

        team.setTeamIsActive(0L);  // 모집 상태를 마감으로 설정
        teamRepository.save(team);
    }


    @Override
    @Transactional
    public List<TeamResponse.TeamListResponse> getTeamList(Optional<String> searchTermOpt, Optional<TeamType> teamTypeOpt, List<String> positions, List<String> techStacks, String sortBy) {

        List<Object[]> results = teamRepository.findAllWithLikesAndMember();

        List<TeamResponse.TeamListResponse> teamList = results.stream()
                .map(result -> {
                    Team team = (Team) result[0];
                    Long likeCount = (Long) result[1];

                    if (teamTypeOpt.isPresent() && !team.getTeamType().equals(teamTypeOpt.get())) {
                        return null;
                    }


                    // `teamPosition` filter
                    if (!positions.isEmpty() && positions.stream().noneMatch(pos -> team.getTeamPosition().contains(pos))) {
                        return null;
                    }

                    // `teamTechStack` filter
                    List<String> teamStackNames = team.getTeamTechStacks().stream()
                            .map(TeamTechStack::getName)
                            .collect(Collectors.toList());
                    if (!techStacks.isEmpty() && techStacks.stream().noneMatch(teamStackNames::contains)) {
                        return null;
                    }

                    // Search term filtering
                    if (searchTermOpt.isPresent()) {
                        String searchTerm = searchTermOpt.get().toLowerCase();
                        boolean matches = team.getTeamTitle().toLowerCase().contains(searchTerm) ||
                                team.getMember().getName().toLowerCase().contains(searchTerm) ||
                                team.getTeamContent().toLowerCase().contains(searchTerm);
                        if (!matches) {
                            return null;
                        }
                    }

                    // Construct MemberInfo
                    CommunityResponse.MemberInfo memberInfo = new CommunityResponse.MemberInfo(
                            team.getMember().getId(),
                            team.getMember().getImageUrl(),
                            team.getMember().getName()
                    );

                    // Shortened content
                    String shortenedContent = team.getTeamContent();
                    if (shortenedContent.length() > 80) {
                        shortenedContent = shortenedContent.substring(0, 80) + "...";
                    }

                    return TeamConverter.toTeamListResponse(team, memberInfo, likeCount, shortenedContent);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Sorting
        switch (sortBy.toLowerCase()) {
            case "likes":
                teamList.sort(Comparator.comparingLong(TeamResponse.TeamListResponse::getLikes).reversed());
                break;
            case "views":
                teamList.sort(Comparator.comparingLong(TeamResponse.TeamListResponse::getViews).reversed());
                break;
            default:
                teamList.sort(Comparator.comparing(TeamResponse.TeamListResponse::getCreatedAt).reversed());
                break;
        }

        return teamList;
    }


    @Override
    @Transactional
    public TeamResponse.TeamDetailResponse getTeamDetail(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new CommunityHandler(ErrorStatus.TEAM_NOT_FOUND));

        if(team.getDeletedAt() != null) {
            throw new CommunityHandler(ErrorStatus.TEAM_DELETED);
        }

        Long Likecount = likeRepository.countByTeamId(id);
        CommunityResponse.MemberInfo memberInfo = new CommunityResponse.MemberInfo(
                team.getMember().getId(),
                team.getMember().getImageUrl(),
                team.getMember().getName()
        );

        return TeamConverter.toTeamDetailResponse(team, memberInfo, Likecount);
    }

    @Override
    @Transactional
    public void deleteTeam(Long id, Long userId) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new TeamHandler(ErrorStatus.TEAM_NOT_FOUND));

        if (team.getDeletedAt() != null) {
            throw new CommunityHandler(ErrorStatus.TEAM_DELETED);
        }


        if (!team.getMember().getId().equals(userId)) {
            throw new CommunityHandler(ErrorStatus.UNAUTHORIZED_USER);
        }

        team.deleteSoftly();

        teamRepository.save(team);
    }
}
