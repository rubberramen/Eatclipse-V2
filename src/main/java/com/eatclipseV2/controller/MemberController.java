package com.eatclipseV2.controller;

import com.eatclipseV2.common.MessageDto;
import com.eatclipseV2.common.StringConst;
import com.eatclipseV2.domain.member.dto.MemberEditFormDto;
import com.eatclipseV2.domain.member.dto.MemberFormDto;
import com.eatclipseV2.domain.member.dto.MemberLoginFormDto;
import com.eatclipseV2.entity.Member;
import com.eatclipseV2.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/new")
    public String createMember(@ModelAttribute MemberFormDto memberFormDto) {
        return "members/createMemberForm";
    }

    @PostMapping("/new")
    public String createMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "members/createMemberForm";
        }
        try {
            memberService.saveMember(memberFormDto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "members/createMemberForm";
        }

        MessageDto messageDto = new MessageDto("회원 가입이 완료되었습니다. 로그인 해주세요",
                "/members/login", RequestMethod.GET, null);
        return showMessageAndRedirect(messageDto, model);
    }

    @GetMapping("/login")
    public String login(@ModelAttribute MemberLoginFormDto memberLoginFormDto, Model model) {
        return "members/memberLoginForm";
    }

    @PostMapping("/login")
    public String login(@Valid MemberLoginFormDto memberLoginFormDto, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            return "members/memberLoginForm";
        }

        Member loginMember = memberService.login(memberLoginFormDto.getNickName(), memberLoginFormDto.getPassword());
        log.info("loginMember : {}", loginMember);

        if (loginMember == null) {
            model.addAttribute("loginFail", "닉네임 또는 비밀번호가 맞지 않습니다");
            return "members/memberLoginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(StringConst.LOGIN_MEMBER, loginMember);

        MessageDto messageDto = new MessageDto("로그인 되었습니다.", "/", RequestMethod.GET, null);
//        return "redirect:" + redirectURL;
        return showMessageAndRedirect(messageDto, model);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/chargeCash")
    public String chargeCash(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                             Model model) {

        Member member = memberService.getMemberInfoByNickName(loginMember.getNickName());
        model.addAttribute("member", member);
        model.addAttribute("currentCash", member.getCash());

        return "members/chargeCashForm";
    }

    @PostMapping("/chargeCash")
    public String chargeCash(@RequestParam int amount,
                              @SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                             Model model) {

        if (amount < 10000) {
            model.addAttribute("member", loginMember);
            model.addAttribute("currentCash", loginMember.getCash());
            model.addAttribute("error", "10,000원 이상 충전해 주세요");
            return "members/chargeCashForm";
        }

        model.addAttribute("member", loginMember);
        memberService.chargeCash(loginMember.getNickName(), amount);

        Member member = memberService.getMemberInfoByNickName(loginMember.getNickName());
        int currentCash = member.getCash();
        String message = String.valueOf(amount) + "원이 충전되어 현재 캐시는 " + currentCash + "원 입니다";

        MessageDto messageDto = new MessageDto(message, "/", RequestMethod.GET, null);
        return showMessageAndRedirect(messageDto, model);
    }

    @GetMapping("/{memberId}")
    public String member(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                             @PathVariable Long memberId, Model model) {
        Member member = memberService.getMemberInfoByMemberId(loginMember.getId());
        MemberEditFormDto formDto = Member.createEditForm(member);
        model.addAttribute("member", member);
        model.addAttribute("formDto", formDto);
        return "members/memberForm";
    }

    @GetMapping("/{memberId}/edit")
    public String memberEdit(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                             @PathVariable Long memberId, Model model) {
        Member member = memberService.getMemberInfoByMemberId(loginMember.getId());
        MemberEditFormDto editForm = Member.createEditForm(member);
        model.addAttribute("member", member);
        model.addAttribute("editForm", editForm);
        return "members/memberEditForm";
    }

    @PostMapping("/{memberId}/edit")
    public String memberEdit(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                             @PathVariable Long memberId, Model model,
                             @Valid @ModelAttribute("editForm") MemberEditFormDto memberEditFormDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("member", loginMember);
            return "members/memberEditForm";
        }

        memberService.updateMemberInfo(memberEditFormDto);

        MessageDto messageDto = new MessageDto("정보 수정이 완료되었습니다",
                "/", RequestMethod.GET, null);

        return showMessageAndRedirect(messageDto, model);
    }

    private String showMessageAndRedirect(final MessageDto messageDto, Model model) {
        model.addAttribute("params", messageDto);
        return "common/messageRedirect";
    }
}
