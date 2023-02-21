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

    /**
     * ID로 멤버 검색
     * @param username the username identifying the user whose data is required.
     * @return 유저정보
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 멤버 ID로 검색
        Optional<Member>  _member = this.memberRepository.findById(username);
        // 멤버 Role
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 일치하는 멤버 판정
        if (_member.isPresent()){
            // 일치하는 멤버가 존재하는 경우
            Member member = _member.get();

            if ("admin".equals(username)) {
                // 멤버 ID가 "admin"일 경우 ROLE_ADMIN을 부여
                authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
            } else {
                // 멤버 ID가 "admin"가 아닐 경우 ROLE_MEMBER를 부여
                authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
            }
            return new User(member.getId(), member.getPass(), authorities);
        } else {
            // 일치하는 멤버가 존재하지 않는 경우
            return null;
        }
    }
}
