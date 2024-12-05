package com.example.securitydemo.security;

import com.example.securitydemo.member.Member;
import com.example.securitydemo.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Member findedMember = memberRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("유저를 찾을 수 없습니다"));

        List<GrantedAuthority> authorities = findedMember.getMemberRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleType().name()))
                .collect(Collectors.toList());

        return new User(findedMember.getUsername(),findedMember.getPassword(), authorities);
    }
}
