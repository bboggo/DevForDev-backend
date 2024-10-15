package com.backend.devfordev.repository;

import com.backend.devfordev.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Heart, Long> {

    Long countByCommunityId(Long communityId);
}
