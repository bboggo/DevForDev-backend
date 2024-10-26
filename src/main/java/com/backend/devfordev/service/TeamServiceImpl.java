package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.CommunityHandler;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.apiPayload.exception.handler.TeamHandler;
import com.backend.devfordev.converter.CommunityConverter;
import com.backend.devfordev.converter.TeamConverter;
import com.backend.devfordev.domain.*;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.dto.TeamRequest;
import com.backend.devfordev.dto.TeamResponse;
import com.backend.devfordev.repository.MemberRepository;
import com.backend.devfordev.repository.TeamRepository;
import com.backend.devfordev.repository.TeamTagRepository;
import com.backend.devfordev.repository.TeamTechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService{
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamTagRepository teamTagRepository;
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
}
