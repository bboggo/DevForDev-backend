package com.backend.devfordev.repository;

import com.backend.devfordev.domain.PortfolioCareer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioCareerRepository extends JpaRepository<PortfolioCareer, Long> {
}