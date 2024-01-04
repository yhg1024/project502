package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member") // 기본주소
@RequiredArgsConstructor // 의존성 자동 주입
public class MemberContorller implements ExceptionProcessor {

    private final Utils utils;
    private final JoinService joinService;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form, Model model) {
        commonProcess("join", model);
        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors, Model model) { // Ps : 처리하는 부분
        commonProcess("join", model);

        joinService.process(form, errors);

        if (errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        commonProcess("login", model);
        return utils.tpl("member/login");
    }


    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "join";
        String pageTitle = Utils.getMessage("회원가입", "commons");
        if (mode.equals("login")) {
            pageTitle = Utils.getMessage("로그인", "commons");
        }
        model.addAttribute("pageTitle", pageTitle);
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

    /*@ResponseBody
    @GetMapping("info")
    public void info() {
        if (memberUtil.isLogin()) {
            Member member = memberUtil.getMember();
            System.out.println(member); // 로그인 상태일땐 회원정보를 콘솔에 보여준다.
        } else {
            System.out.println("미로그인 상태");
        }
    }*/
}
