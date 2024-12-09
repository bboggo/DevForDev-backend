package com.backend.devfordev.repository.ProjectRepository;

import com.backend.devfordev.domain.ProjectEntity.ProjectLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectLinkRepository extends JpaRepository<ProjectLink, Long> {
}
