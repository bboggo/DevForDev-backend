package com.backend.devfordev.repository;

import com.backend.devfordev.domain.Community;
import com.backend.devfordev.domain.enums.CommunityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByCommunityCategory(CommunityCategory category);

    @Query("SELECT c, (SELECT COUNT(h) FROM Heart h WHERE h.community = c) as likeCount " +
            "FROM Community c JOIN FETCH c.member")
    List<Object[]> findAllWithLikesAndMember();
}
