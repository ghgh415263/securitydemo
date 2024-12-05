package com.example.securitydemo.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "member", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<MemberRole> memberRoles = new ArrayList<>();


    @Builder
    private Member(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void addMemberRole(RoleType roleType){
        this.memberRoles.add(new MemberRole(roleType, this));
    }

}

