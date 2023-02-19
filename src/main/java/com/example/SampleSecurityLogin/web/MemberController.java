package com.example.SampleSecurityLogin.web;

import com.example.SampleSecurityLogin.service.member.MemberService;
import com.example.SampleSecurityLogin.utils.dto.ErrorMsg;
import com.example.SampleSecurityLogin.web.form.MemberCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false)String error,
                        @RequestParam(value = "exception", required = false)String exception,
                        Model model) {

        // 예외 발생시 알림 표시를 위한 값 설정
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "member-login";
    }

    @GetMapping("/signup")
    public String signup(MemberCreateForm memberCreateForm) {
        return "member-signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult, Model model) {

        // 유효성 검사 판정
        if (bindingResult.hasErrors()) {
            // 유효성 검사 에러 발생시 에러 메세지를 추출하여 리스트에 추가
            List<ErrorMsg> errorList = new ArrayList<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorList.add(new ErrorMsg(error.getDefaultMessage()));
            }
            if (!errorList.isEmpty()) {
                // 에러 메세지 리스트를 모델에 추가
                model.addAttribute("validationErrors", errorList);
            }
            return "member-signup";
        }

        try {
            // 멤버등록처리
            memberService.create(memberCreateForm.getId(), memberCreateForm.getPass());
        }catch(DataIntegrityViolationException e) {
            // 중복발생
            e.printStackTrace();
            model.addAttribute("validationErrors", new ErrorMsg("이미 등록된 사용자입니다."));
            return "member-signup";
        }catch(Exception e) {
            // 예외발생
            e.printStackTrace();
            model.addAttribute("validationErrors", new ErrorMsg(e.getMessage()));
            return "member-signup";
        }
        
        return "redirect:/";
    }
}
