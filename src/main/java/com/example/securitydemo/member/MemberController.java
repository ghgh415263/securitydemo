package com.example.securitydemo.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    @GetMapping("/join")
    public String createMember(){

        Member member = Member.builder().username("user").password(passwordEncoder.encode("12345")).build();
        member.addMemberRole(RoleType.USER);

        memberRepository.save(member);

        return "success";
    }

    @GetMapping("/members")
    public String getMember(HttpSession httpSession){
        log.info("");
        return "MemberInfo";
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return "admin";
    }
}
