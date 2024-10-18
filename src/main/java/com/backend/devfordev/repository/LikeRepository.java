package com.backend.devfordev.repository;

import com.backend.devfordev.domain.Heart;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.enums.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Heart, Long> {

    // 커뮤니티의 좋아요 수를 카운트 (like 필드를 통해 communityId와 매칭)
    @Query("SELECT COUNT(h) FROM Heart h WHERE h.likeId = :communityId AND h.likeType = 'COMMUNITY'")
    Long countByCommunityId(Long communityId);

    // 특정 게시물에 대한 전체 좋아요 수 계산
    @Query("SELECT COUNT(h) FROM Heart h WHERE h.likeId = :likeId AND h.likeType = :likeType")
    Long countByLikeIdAndLikeType(@Param("likeId") Long likeId, @Param("likeType") LikeType likeType);
    // 유저가 특정 게시물에 좋아요를 눌렀는지 확인하는 메서드
    Optional<Heart> findByMemberAndLikeIdAndLikeType(Member member, Long likeId, LikeType likeType);
}
