package com.example.securitydemo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberMethodSecurityController {

    private final MemberService service;

    @GetMapping("/method")
    public String test(){
        service.invokePreAuth();
        return "good";
    }
}
