package com.eatclipseV2.service;

import com.eatclipseV2.domain.member.dto.MemberFormDto;
import com.eatclipseV2.entity.Address;
import com.eatclipseV2.entity.Member;
import com.eatclipseV2.entity.enums.Role;
import com.eatclipseV2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(MemberFormDto memberFormDto) {

        Member member = Member.createMember(memberFormDto);
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member;
    }

    public Member login(String nickName, String password) {
        List<Member> byNickName = memberRepository.findByNickName(nickName);

        if (byNickName.isEmpty()) {
            return null;
        }
        for (Member member : byNickName) {
            if (member.getPassword().equals(password)) {
                return member;
            } else {
                return null;
            }
        }
        return null;
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findByNickName = memberRepository.findByNickName(member.getNickName());
        if (!findByNickName.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다. 닉네임을 바꿔주세요");
        }
    }
}
