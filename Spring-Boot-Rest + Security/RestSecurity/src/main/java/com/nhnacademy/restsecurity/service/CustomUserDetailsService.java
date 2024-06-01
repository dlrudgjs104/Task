package com.nhnacademy.restsecurity.service;

import com.nhnacademy.restsecurity.domain.Member;
import com.nhnacademy.restsecurity.domain.Role;
import com.nhnacademy.restsecurity.request.LoginFailCountRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final String HASH_LOGIN_LOCK = "LoginLock:";
    private final MemberService memberService;
    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(MemberService memberService, LoginService loginService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.loginService = loginService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws RuntimeException {
        Member member = memberService.getMember(id);

        if (member == null) {
            throw new UsernameNotFoundException(id);
        }

        if(loginService.loginLockMemberCheck(HASH_LOGIN_LOCK + id)){
            throw new RuntimeException();
        }

        if (member.getRole() == Role.ADMIN) {
            return new User(member.getId(), passwordEncoder.encode(member.getPassword()), Arrays.asList(new SimpleGrantedAuthority("admin")));
        } else if(member.getRole() == Role.MEMBER) {
            return new User(member.getId(), passwordEncoder.encode(member.getPassword()), Arrays.asList(new SimpleGrantedAuthority("member")));
        } else {
            return new User(member.getId(), passwordEncoder.encode(member.getPassword()), Arrays.asList(new SimpleGrantedAuthority("google")));
        }


    }
}
