package com.eatclipseV2.service;

import com.eatclipseV2.domain.member.dto.MemberEditFormDto;
import com.eatclipseV2.domain.member.dto.MemberFormDto;
import com.eatclipseV2.entity.Address;
import com.eatclipseV2.entity.Member;
import com.eatclipseV2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
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
    }

    public Member getMemberInfoByNickName(String nickName) {
        Member member = memberRepository.findByNickName(nickName);
        if (member == null) {
            return null;
        } else return member;
    }

    public Member getMemberInfoByMemberId(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.orElse(null);
    }

    public void updateMemberInfo(MemberEditFormDto memberEditFormDto) {
        Member member = memberRepository.findByNickName(memberEditFormDto.getNickName());
        member.setName(memberEditFormDto.getName());
        Address address = new Address(memberEditFormDto.getCity(), memberEditFormDto.getStreet(), memberEditFormDto.getZipcode());
        member.setAddress(address);
        memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findByNickName = memberRepository.findByNickName(member.getNickName());
        if (findByNickName != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다. 닉네임을 바꿔주세요");
        }
    }
}
