package com.example.securitydemo.security;

import com.example.securitydemo.member.Member;
import com.example.securitydemo.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Member findedMember = memberRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("유저를 찾을 수 없습니다"));

        return new User(findedMember.getUsername(),findedMember.getPassword(), List.of(new SimpleGrantedAuthority(findedMember.getRole())));
    }
}
