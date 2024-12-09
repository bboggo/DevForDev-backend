package com.backend.devfordev.converter;

import com.backend.devfordev.domain.*;
import com.backend.devfordev.dto.PortfolioRequest;
import com.backend.devfordev.dto.ProjectRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectConverter {

    public static Project toProject(ProjectRequest.ProjectCreateRequest request, Member member, String imageUrl) {

        Project project = Project.builder()
                .projectTitle(request.getProjectTitle())
                .projectContent(request.getProjectContent())
                .projectCategory(request.getProjectCategory())
                .projectImageUrl(imageUrl)
                .projectViews(0L)
                .member(member)
                .build();

        // techStacks 리스트를 쉼표로 구분된 문자열로 변환하여 저장
        project.setTags(Collections.singletonList(String.join(",", request.getTags())));
        return project;
    }

    public static List<ProjectLink> toProjectLinks(List<ProjectRequest.ProjectCreateRequest.LinkRequest> linkRequests, Project project) {
        return linkRequests.stream()
                .map(linkRequest -> ProjectLink.builder()
                        .type(linkRequest.getType())
                        .url(linkRequest.getUrl())
                        .project(project)
                        .build())
                .collect(Collectors.toList());
    }
}
