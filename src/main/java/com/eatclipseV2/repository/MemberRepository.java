package com.eatclipseV2.repository;

import com.eatclipseV2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNickName(String nickName);

    Optional<Member> findById(Long memberId);
}
