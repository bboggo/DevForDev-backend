package com.backend.devfordev.service;

import com.backend.devfordev.dto.ProjectRequest;
import com.backend.devfordev.dto.ProjectResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {
    public ProjectResponse.ProjectCreateResponse createProject(ProjectRequest.ProjectCreateRequest request, Long userId, MultipartFile portImage);
}
