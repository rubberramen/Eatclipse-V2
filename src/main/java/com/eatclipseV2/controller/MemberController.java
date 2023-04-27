package com.eatclipseV2.controller;

import com.eatclipseV2.common.StringConst;
import com.eatclipseV2.domain.member.dto.MemberFormDto;
import com.eatclipseV2.domain.member.dto.MemberLoginForm;
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

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute MemberLoginForm memberLoginForm, Model model) {
        return "members/memberLoginForm";
    }

    @PostMapping("/login")
    public String login(@Valid MemberLoginForm memberLoginForm, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            return "members/memberLoginForm";
        }

        Member loginMember = memberService.login(memberLoginForm.getNickName(), memberLoginForm.getPassword());
        log.info("loginMember : {}", loginMember);

        if (loginMember == null) {
            model.addAttribute("loginFail", "닉네임 또는 비밀번호가 맞지 않습니다");
            return "members/memberLoginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(StringConst.LOGIN_MEMBER, loginMember);

        return "redirect:" + redirectURL;
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        session.invalidate();

        return "redirect:/";
    }
}
