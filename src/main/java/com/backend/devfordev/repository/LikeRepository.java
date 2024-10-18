package com.backend.devfordev.repository;

import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Heart, Long> {

    // 커뮤니티의 좋아요 수를 카운트 (like 필드를 통해 communityId와 매칭)
    @Query("SELECT COUNT(h) FROM Heart h WHERE h.likeId = :communityId AND h.likeType = 'COMMUNITY'")
    Long countByCommunityId(Long communityId);
}
