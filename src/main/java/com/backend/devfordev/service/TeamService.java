package com.backend.devfordev.service;

import com.backend.devfordev.domain.enums.TeamType;
import com.backend.devfordev.dto.CommunityResponse;
import com.backend.devfordev.dto.TeamRequest;
import com.backend.devfordev.dto.TeamResponse;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    public TeamResponse.TeamCreateResponse createTeam(TeamRequest.TeamCreateRequest request, Long userId);
    public void closeRecruitment(Long teamId, Long userId);

    public List<TeamResponse.TeamListResponse> getTeamList(Optional<String> searchTermOpt, Optional<TeamType> teamTypeOpt, List<String> positions, List<String> techStacks, String sortBy);
    public TeamResponse.TeamDetailResponse getTeamDetail(Long id);
    public void deleteTeam(Long id, Long userId);
    public List<CommunityResponse.MemberInfo> searchMembersByNickname(String name);
}
