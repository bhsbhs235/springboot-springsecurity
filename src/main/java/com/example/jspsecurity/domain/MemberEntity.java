package com.example.jspsecurity.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(length = 15, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(nullable = false, unique = true)
    @Email(message = "please provide a valid e-mail")
    @NotEmpty(message = "please provide an e-mail")
    private String email;

    @Column(name = "reset_token")
    private String resetToken;

    @Builder
    public MemberEntity(Long id, String username, String password, String email, String resetToken) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.resetToken = resetToken;
    }
}