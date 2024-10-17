package com.backend.devfordev.repository;

import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Heart, Long> {

    Long countByCommunityId(Long communityId);

}
