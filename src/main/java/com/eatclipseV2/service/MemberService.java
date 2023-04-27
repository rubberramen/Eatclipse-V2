package com.eatclipseV2.service;

import com.eatclipseV2.domain.member.dto.MemberFormDto;
import com.eatclipseV2.entity.Member;
import com.eatclipseV2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        Member member = memberRepository.findByNickName(nickName);
        if (member == null) {
            return null;
        }

        if (member.getPassword().equals(password)) {
            return member;
        } else return null;

    }

    public void chargeCash(String nickName, int amount) {

        Member member = memberRepository.findByNickName(nickName);
        int oldAmount = member.getCash();
        int newAmount = oldAmount + amount;
        member.setCash(newAmount);
        memberRepository.save(member);
    }

    public Member getMemberInfo(String nickName) {
        Member member = memberRepository.findByNickName(nickName);
        if (member == null) {
            return null;
        } else return member;
    }

    private void validateDuplicateMember(Member member) {
        Member findByNickName = memberRepository.findByNickName(member.getNickName());
        if (findByNickName != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다. 닉네임을 바꿔주세요");
        }
    }
}
