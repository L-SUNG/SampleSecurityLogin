package com.example.SampleSecurityLogin.web;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class PageController {

    @GetMapping("/")
    public String index(HttpSession session) {

        // 스프링 시큐리티 컨텍스트 취득
        Object securityContextObject = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if(securityContextObject != null) {
            SecurityContext securityContext = (SecurityContext)securityContextObject;
            Authentication authentication = securityContext.getAuthentication();
            // 인증정보로 부터 멤버 ID를 취득하여 세션에 설정
            session.setAttribute("member", authentication.getName());
        }

        return "index";
    }

}
