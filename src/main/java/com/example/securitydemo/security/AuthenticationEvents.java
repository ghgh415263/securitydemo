package com.example.securitydemo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component // 로그인 관련 이벤트
@Slf4j
public class AuthenticationEvents {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
        log.info("로그인 성공 : {}", successEvent.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureEvent) {
        log.error("로그인 실패 : {} 원인 : {}", failureEvent.getAuthentication().getName(), failureEvent.getException().getMessage());
    }

}
