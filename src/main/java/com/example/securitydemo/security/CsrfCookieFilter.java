package com.example.securitydemo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {

    // doFilterInternal 메서드를 오버라이드하여 실제 필터의 로직을 구현
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // CsrfToken 클래스의 인스턴스를 요청 속성에서 가져오기 (현재 요청에 대한 CSRF 토큰 정보를 가져오기)
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        // 응답 헤더에 CSRF 토큰을 설정
        if(null != csrfToken.getHeaderName()){
            // CSRF 토큰의 이름(헤더 이름)과 값을 응답 헤더에 설정 -> 클라이언트 측에서 이 헤더를 읽어 CSRF 토큰을 알 수 있게 됨.
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }

        // 다음 필터로 요청과 응답을 전달
        filterChain.doFilter(request, response);
    }

}