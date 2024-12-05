package com.example.securitydemo.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {

    USER("일반사용자"),
    ADMIN("관리자");
    private final String value;

}
