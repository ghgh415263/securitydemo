package com.example.securitydemo.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {

    @PreAuthorize("hasAuthority('ADMIN')")
    public void invokePreAuth(){
        log.info("invokePreAuth");
    }

}
