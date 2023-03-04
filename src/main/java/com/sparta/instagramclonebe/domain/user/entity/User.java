package com.sparta.instagramclonebe.domain.user.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String useremail;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    private User(String useremail, String password, UserRoleEnum role, String nickname) {
        this.useremail = useremail;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
    }

    public static User of(String useremail, String password, UserRoleEnum role, String nickname) {
        return User.builder()
                .useremail(useremail)
                .password(password)
                .role(role)
                .nickname(nickname)
                .build();
    }

}