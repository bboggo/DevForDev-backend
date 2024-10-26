package com.backend.devfordev.repository;

import com.backend.devfordev.domain.TeamTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamTagRepository extends JpaRepository<TeamTag, Long> {
    Optional<TeamTag> findByName(String name);
}
