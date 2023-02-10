package com.example.SampleSecurityLogin.service.member;

import com.example.SampleSecurityLogin.domain.member.Member;
import com.example.SampleSecurityLogin.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String username, String password) {
        // 회원등록 Form에서 받아온 값 설정
        Member member = new Member();
        member.setId(username);
        // 패스워드 암호화
        member.setPass(passwordEncoder.encode(password));
        this.memberRepository.save(member);

        return member;
    }
}
