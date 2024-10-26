package com.backend.devfordev.service;

import com.backend.devfordev.dto.TeamRequest;
import com.backend.devfordev.dto.TeamResponse;

public interface TeamService {
    public TeamResponse.TeamCreateResponse createTeam(TeamRequest.TeamCreateRequest request, Long userId);
}
