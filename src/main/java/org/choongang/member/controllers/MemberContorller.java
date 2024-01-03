package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member") // 기본주소
@RequiredArgsConstructor // 의존성 자동 주입
public class MemberContorller implements ExceptionProcessor {

    private final Utils utils;
    private final JoinService joinService;
    private final MemberUtil memberUtil;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form) {

        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors) { // Ps : 처리하는 부분

        joinService.process(form, errors);

        if (errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login() {

        return utils.tpl("member/login");
    }

    /*@ResponseBody
    @GetMapping("/info")
    public void info(Principal principal) {
        String username = principal.getName();
        System.out.printf("username=%s%n", username);
    }*/

    /*@ResponseBody
    @GetMapping("info")
    public void info() {
        MemberInfo memberInfo = (MemberInfo) SecurityContextHolder
                                    .getContext()
                                    .getAuthentication() // LoginSuccessHandler에 있다.
                                    .getPrincipal();

        System.out.println(memberInfo);
    }*/

    /*@ResponseBody
    @GetMapping("/info")
    public void info(@AuthenticationPrincipal MemberInfo memberInfo) {
        System.out.println(memberInfo);
    }*/

    @ResponseBody
    @GetMapping("info")
    public void info() {
        if (memberUtil.isLogin()) {
            Member member = memberUtil.getMember();
            System.out.println(member); // 로그인 상태일땐 회원정보를 콘솔에 보여준다.
        } else {
            System.out.println("미로그인 상태");
        }
    }
}
