package com.example.jspsecurity.service.impl;

import com.example.jspsecurity.dto.MemberDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.jspsecurity.domain.MemberEntity;
import com.example.jspsecurity.repository.MemberRepository;
import com.example.jspsecurity.service.MemberService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public void joinMember(MemberDto memberDto) {
        //비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        memberRepository.save(memberDto.toEntity());
    }
    
    @Override
    public List<MemberDto> findMemberByEmail(String email){

        Optional<MemberEntity> member = memberRepository.findByEmail(email);
        List<MemberDto> members = new ArrayList<MemberDto>();

        if(member.isPresent()){
            MemberDto memberDto = modelMapper.map(member.get(), MemberDto.class);
            members.add(memberDto);
        }

        return members;
    }

    @Override
    public List<MemberDto> findMemberByResetToken(String resetToken){

        Optional<MemberEntity> member = memberRepository.findByResetToken(resetToken);
        List<MemberDto> members = new ArrayList<MemberDto>();

        if(member.isPresent()){
            MemberDto memberDto = modelMapper.map(member.get(), MemberDto.class);
            members.add(memberDto);
        }

        return members;
    }

    @Override
    public void saveMember(MemberDto memberDto){
        memberRepository.save(memberDto.toEntity());
    }
}