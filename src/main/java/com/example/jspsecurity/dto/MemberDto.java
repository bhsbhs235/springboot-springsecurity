package com.example.jspsecurity.dto;

import com.example.jspsecurity.domain.MemberEntity;
import lombok.Setter;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Setter
@Getter
@ToString
@NoArgsConstructor

public class MemberDto{
    
    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private String email;
    private String resetToken;

    public MemberEntity toEntity(){
        return MemberEntity.builder()
        .id(id)
        .username(username)
        .password(password)
        .email(email)
        .resetToken(resetToken)
        .build();
    }

    @Builder
    public MemberDto(Long id, String username, String password, String email, String resetToken){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.resetToken = resetToken;
    }
}


