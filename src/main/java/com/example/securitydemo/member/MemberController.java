package com.example.securitydemo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    @PostMapping("/join")
    public String createMember(){

        Member member = Member.builder().username("user").password(passwordEncoder.encode("12345")).role("admin").build();

        memberRepository.save(member);

        return "success";
    }

    @GetMapping("/members")
    public String getMember(){
        return "MemberInfo";
    }
}
