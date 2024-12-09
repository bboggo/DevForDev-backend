package com.backend.devfordev.repository;

import com.backend.devfordev.domain.CommunityComment;
import com.backend.devfordev.domain.ProjectLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectLinkRepository extends JpaRepository<ProjectLink, Long> {
}
