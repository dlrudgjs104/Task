package com.nhnacademy.restsecurity.service;

import com.nhnacademy.restsecurity.domain.Member;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final String HASH_NAME = "Member:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final LoginService loginService;

    public MemberService(RedisTemplate<String, Object> redisTemplate, LoginService loginService) {
        this.redisTemplate = redisTemplate;
        this.loginService = loginService;
    }

    public Member saveMember(Member member) {
        if(redisTemplate.opsForHash().putIfAbsent(HASH_NAME, member.getId(), member)){
            loginService.initializeLoginFailCount(member.getId());
            return member;
        } else {
            return null;
        }
//        return redisTemplate.opsForHash().putIfAbsent(HASH_NAME, member.getId(), member) ? member : null;
    }

    public Member getMember(String id) {
        return (Member) redisTemplate.opsForHash().get(HASH_NAME, id);
    }

    public List<Member> getMembers() {
        return redisTemplate.opsForHash().values(HASH_NAME).stream()
                .map(value -> (Member) value)
                .collect(Collectors.toList());
    }

    public Member deleteMember(String id) {
        Member member = getMember(id);
        redisTemplate.opsForHash().delete(HASH_NAME, id);

        return member;
    }

    public Member updateMember(Member member) {
        Member updatedMember = getMember(member.getId());
        updatedMember.setPassword(member.getPassword());
        updatedMember.setName(member.getName());
        updatedMember.setAge(member.getAge());
        updatedMember.setRole(member.getRole());

        redisTemplate.opsForHash().put(HASH_NAME, updatedMember.getId(), updatedMember);

        return member;
    }
}
