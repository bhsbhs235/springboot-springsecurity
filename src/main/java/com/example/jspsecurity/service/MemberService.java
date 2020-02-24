package com.example.jspsecurity.service;

import java.util.List;

import com.example.jspsecurity.dto.MemberDto;

public interface MemberService{

    public void joinMember(MemberDto memberDto);
    public List<MemberDto> findMemberByEmail(String email);
    public List<MemberDto> findMemberByResetToken(String resetToken);
    public void saveMember(MemberDto memberDto);
}