package com.example.SampleSecurityLogin.service.member;

import com.example.SampleSecurityLogin.domain.member.Member;
import com.example.SampleSecurityLogin.domain.member.MemberRepository;
import com.example.SampleSecurityLogin.domain.member.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 멤버ID로 검색
        Optional<Member>  _member = this.memberRepository.findById(username);

        if(_member.isEmpty()) {
            throw new UsernameNotFoundException("등록된 멤버를 찾을 수 없습니다.");
        }

        Member member = _member.get();
        List<GrantedAuthority> authorites = new ArrayList<>();
        if ("admin".equals(username)) {
            authorites.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            authorites.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
        }
        return new User(member.getId(), member.getPass(), authorites);
    }
}
