package com.sparta.instagramclonebe.domain.user.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    private User(String username, String password, UserRoleEnum role, String nickname) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
    }

    public static User of(String username, String password, UserRoleEnum role, String nickname) {
        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .nickname(nickname)
                .build();
    }

}