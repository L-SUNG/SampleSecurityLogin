package com.example.SampleSecurityLogin.web;

import com.example.SampleSecurityLogin.service.member.MemberService;
import com.example.SampleSecurityLogin.web.form.MemberCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "member-login";
    }

    @GetMapping("/signin")
    public String signup(MemberCreateForm memberCreateForm) {
        return "member-signin";
    }

    @PostMapping("/signin")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member-signin";
        }

        memberService.create(memberCreateForm.getId(), memberCreateForm.getPass());

        return "redirect:/";
    }



}
