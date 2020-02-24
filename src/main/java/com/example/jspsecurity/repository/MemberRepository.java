package com.example.jspsecurity.repository;

import java.util.Optional;

import com.example.jspsecurity.domain.MemberEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
    Optional<MemberEntity> findByUsername(String username);
    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByResetToken(String resetToken);
}