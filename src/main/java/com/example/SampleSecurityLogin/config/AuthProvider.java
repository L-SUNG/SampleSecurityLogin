package com.example.SampleSecurityLogin.config;

import com.example.SampleSecurityLogin.service.member.MemberSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 인증 프로바이더
 * 로그인시 사용자가 입력한 아이디와 비밀번호를 확인하고 해당 권한을 주는 클래스
 * <a href="https://dabok407.tistory.com/37">참고자료</a>
 */
@Component("authProvider")
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    MemberSecurityService memberSecurityService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String id = (String) authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails user = memberSecurityService.loadUserByUsername(id);

        if (null == user){
            // 유저가 존재하지 않을 경우
            throw new UsernameNotFoundException(id);
        }else if(!passwordEncoder.matches(password, user.getPassword())){
            // 패스워드가 일치하지 않을 경우
            throw new BadCredentialsException(id);
        }else if(!user.isAccountNonLocked()){
            // 멤버 계정 락 여부
            throw new LockedException(id);
        }else if(!user.isEnabled()){
            // 멤버 계정 이용가능 여부
            throw new DisabledException(id);
        }else if(!user.isAccountNonExpired()){
            // 멤버 계정 만료 여부
            throw new AccountExpiredException(id);
        }else if(!user.isCredentialsNonExpired()){
            // 멤버 계정 패스워드 여부
            throw new CredentialsExpiredException(id);
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        // 로그인 인증 토큰
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(id, password, user.getAuthorities());
        token.setDetails(user);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
