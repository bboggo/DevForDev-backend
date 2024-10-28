package com.backend.devfordev.service;

import com.backend.devfordev.dto.TeamRequest;
import com.backend.devfordev.dto.TeamResponse;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    public TeamResponse.TeamCreateResponse createTeam(TeamRequest.TeamCreateRequest request, Long userId);
    public void closeRecruitment(Long teamId, Long userId);

    public List<TeamResponse.TeamListResponse> getTeamList(Optional<String> searchTermOpt, String sortBy);
    public TeamResponse.TeamDetailResponse getTeamDetail(Long id);
    public void deleteTeam(Long id, Long userId);
}
