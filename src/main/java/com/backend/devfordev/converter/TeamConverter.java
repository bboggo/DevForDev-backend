package com.backend.devfordev.converter;

import com.backend.devfordev.domain.*;
import com.backend.devfordev.dto.CommunityRequest;
import com.backend.devfordev.dto.TeamRequest;
import com.backend.devfordev.dto.TeamResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class TeamConverter {
    public static Team toTeam(TeamRequest.TeamCreateRequest request, Member member, List<TeamTag> tags) {
        // 기본 팀 정보 설정
        Team team = Team.builder()
                .teamTitle(request.getTeamTitle())
                .teamContent(request.getTeamContent())
                .teamType(request.getTeamType())
                .teamPosition(request.getTeamPosition())
                .teamRecruitmentNum(String.valueOf(request.getTeamRecruitmentNum()))
                .teamIsActive(1L) // 기본 값 설정
                .member(member)
                .build();

        // 기술 스택 변환
        List<TeamTechStack> techStacks = request.getTeamTechStack().stream()
                .map(stack -> TeamTechStack.builder()
                        .name(stack)
                        .team(team)
                        .build())
                .collect(Collectors.toList());

        team.setTeamTechStacks(techStacks); // 기술 스택 설정

        // 태그 매핑 설정
        List<TeamTagMap> tagMaps = tags.stream()
                .map(tag -> TeamTagMap.builder()
                        .team(team)
                        .tag(tag) // 매핑된 태그 엔티티
                        .build())
                .collect(Collectors.toList());

        team.setTeamTagMaps(tagMaps); // 태그 매핑 설정

        return team;
    }

    public static TeamResponse.TeamCreateResponse toTeamCreateResponse(Team team) {
        // 기술 스택 리스트와 태그 리스트 변환
        List<String> techStackNames = team.getTeamTechStacks().stream()
                .map(TeamTechStack::getName)
                .collect(Collectors.toList());

        List<String> tagNames = team.getTeamTagMaps().stream()
                .map(teamTagMap -> teamTagMap.getTag().getName())
                .collect(Collectors.toList());

        return TeamResponse.TeamCreateResponse.builder()
                .teamTitle(team.getTeamTitle())
                .teamContent(team.getTeamContent())
                .teamType(team.getTeamType())
                .teamPosition(team.getTeamPosition())
                .teamRecruitmentNum(Long.valueOf(team.getTeamRecruitmentNum()))
                .teamTechStack(techStackNames)
                .teamTags(tagNames)
                .build();
    }
}
