package com.nhnacademy.restsecurity.controller;

import com.nhnacademy.restsecurity.domain.Member;
import com.nhnacademy.restsecurity.request.MemberRequest;
import com.nhnacademy.restsecurity.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getMembers(){
        return memberService.getMembers();
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable String id) {
        return memberService.getMember(id);
    }

    @PostMapping
    public Member saveMember(@RequestBody MemberRequest memberRequest) {
        return memberService.saveMember(
                new Member(
                        memberRequest.getId(),
                        memberRequest.getPassword(),
                        memberRequest.getName(),
                        memberRequest.getAge(),
                        memberRequest.getRole()
                ));
    }

    @DeleteMapping("/{id}")
    public Member deleteMember(@PathVariable String id) {
        return memberService.deleteMember(id);
    }

    @PutMapping
    public Member updateMember(@RequestBody MemberRequest memberRequest) {
        return memberService.updateMember(
                new Member(
                        memberRequest.getId(),
                        memberRequest.getPassword(),
                        memberRequest.getName(),
                        memberRequest.getAge(),
                        memberRequest.getRole()
                ));
    }

    @GetMapping("/page")
    public Member getMemberPage(Pageable pageable) {
        return new Member();
    }
}
