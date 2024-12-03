package com.backend.devfordev.repository;

import com.backend.devfordev.domain.Team;
import com.backend.devfordev.domain.TeamTagMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamTagMapRepository extends JpaRepository<TeamTagMap, Long> {
    @Modifying
    @Query("DELETE FROM TeamTagMap t WHERE t.team.id = :teamId")
    void deleteAllByTeamId(@Param("teamId") Long teamId);
}
