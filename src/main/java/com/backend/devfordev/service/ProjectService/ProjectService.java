package com.backend.devfordev.service.ProjectService;

import com.backend.devfordev.dto.ProjectDto.ProjectRequest;
import com.backend.devfordev.dto.ProjectDto.ProjectResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {
    public ProjectResponse.ProjectCreateResponse createProject(ProjectRequest.ProjectCreateRequest request, Long userId, MultipartFile portImage);
}
