package com.backend.devfordev.repository.ProjectRepository;


import com.backend.devfordev.domain.ProjectEntity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
