package com.backend.devfordev.repository;

import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.Team;
import com.backend.devfordev.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    boolean existsByTeamAndMember(Team team, Member memberToInvite);
    List<TeamMember> findByTeamId(Long teamId);
}
