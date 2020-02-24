package com.example.jspsecurity.service.impl;

import com.example.jspsecurity.domain.MemberEntity;
import com.example.jspsecurity.domain.Role;
import com.example.jspsecurity.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);
        if (!memberEntity.isPresent()) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (("admin123").equals(username) || ("test123".equals(username))) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new org.springframework.security.core.userdetails.User(memberEntity.get().getUsername(), memberEntity.get().getPassword(), grantedAuthorities);
    }
}