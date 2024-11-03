package com.backend.devfordev.repository;


import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {

    MemberInfo findByMember(Member member);
}
