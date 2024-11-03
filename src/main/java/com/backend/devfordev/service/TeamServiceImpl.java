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
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamTagRepository teamTagRepository;
    private final LikeRepository likeRepository;
    private final TeamTechStackRepository teamTechStackRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberInfoRepository memberInfoRepository;
    @Override
    @Transactional
    public TeamResponse.TeamCreateResponse createTeam(TeamRequest.TeamCreateRequest request, Long userId) {
        // 작성자 확인
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_MEMBER));


        TeamType teamType;
        try {
            teamType = TeamType.valueOf(request.getTeamType());
        } catch (IllegalArgumentException e) {
            throw new TeamHandler(ErrorStatus.INVALID_TEAM);
        }

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

        Team team = TeamConverter.toTeam(request, member, tags, teamType);

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

        team.setTeamIsActive(false);  // 모집 상태를 마감으로 설정
        teamRepository.save(team);
    }


    @Override
    @Transactional
    public List<TeamResponse.TeamListResponse> getTeamList(Optional<String> searchTermOpt, Optional<TeamType> teamTypeOpt, List<String> positions, List<String> techStacks, String sortBy, Optional<Boolean> teamIsActive) {

        List<Object[]> results = teamRepository.findAllWithLikesAndMember();

        List<TeamResponse.TeamListResponse> teamList = results.stream()
                .map(result -> {
                    Team team = (Team) result[0];
                    Long likeCount = (Long) result[1];


                    // 모집 여부 필터링
                    if (teamIsActive.isPresent()) {
                        boolean isRecruiting = teamIsActive.get();
                        if ((isRecruiting && !team.getTeamIsActive()) ||
                                (!isRecruiting && team.getTeamIsActive())) {
                            return null;
                        }
                    }

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
                    MemberInfo memberInfoEntity = memberInfoRepository.findByMember(team.getMember());


                    // Construct MemberInfo
                    CommunityResponse.MemberInfo memberInfo = new CommunityResponse.MemberInfo(
                            team.getMember().getId(),
                            memberInfoEntity.getImageUrl(),
                            memberInfoEntity.getNickname()
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

        if (team.getDeletedAt() != null) {
            throw new CommunityHandler(ErrorStatus.TEAM_DELETED);
        }

        Long Likecount = likeRepository.countByTeamId(id);
        MemberInfo memberInfoEntity = memberInfoRepository.findByMember(team.getMember());

        // Construct MemberInfo
        CommunityResponse.MemberInfo memberInfo = new CommunityResponse.MemberInfo(
                team.getMember().getId(),
                memberInfoEntity.getImageUrl(),
                memberInfoEntity.getNickname()
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


    @Override
    @Transactional
    public List<CommunityResponse.MemberInfo> searchMembersByNickname(String nickname, Long userId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamHandler(ErrorStatus.TEAM_NOT_FOUND));


        // 작성자인지 확인
        if (!team.getMember().getId().equals(userId)) {
            throw new TeamHandler(ErrorStatus.UNAUTHORIZED_USER); // 권한 없음 예외 발생
        }

        MemberInfo memberInfoEntity = memberInfoRepository.findByMember(team.getMember());

        List<Member> members = memberRepository.findByNameContainingIgnoreCase(nickname);
        return members.stream()
                .map(member -> new CommunityResponse.MemberInfo(member.getId(), memberInfoEntity.getNickname(), memberInfoEntity.getImageUrl()))
                .collect(Collectors.toList());

    }


    @Override
    @Transactional
    public TeamResponse.TeamAddMemberResponse AddTeamMember(TeamRequest.TeamAddMemberRequest request, Long userId, Long teamId) {
        // 팀 모집 공고 조회
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamHandler(ErrorStatus.TEAM_NOT_FOUND));

        // 작성자인지 확인
        if (!team.getMember().getId().equals(userId)) {
            throw new TeamHandler(ErrorStatus.UNAUTHORIZED_USER); // 권한 없음 예외 발생
        }


        Member memberToInvite = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));


        // 작성자가 스스로를 추가하려는 경우 예외 처리
        if (team.getMember().getId().equals(request.getMemberId())) {
            throw new TeamHandler(ErrorStatus.CANNOT_ADD_OWNER_AS_MEMBER); // 작성자 추가 불가 예외 발생
        }
        // 이미 팀에 추가된 멤버인지 확인
        boolean isAlreadyMember = teamMemberRepository.existsByTeamAndMember(team, memberToInvite);
        if (isAlreadyMember) {
            throw new TeamHandler(ErrorStatus.ALREADY_TEAM_MEMBER);
        }

        // 팀에 멤버 추가
        TeamMember teamMember = TeamConverter.toTeamMember(team, memberToInvite);

        teamMemberRepository.save(teamMember);

        return TeamConverter.toTeamMemberResponse(teamMember, team, memberToInvite);
    }

}