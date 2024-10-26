package com.backend.devfordev.repository;

import com.backend.devfordev.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
