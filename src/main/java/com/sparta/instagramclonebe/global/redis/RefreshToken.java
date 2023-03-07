package com.sparta.instagramclonebe.global.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@NoArgsConstructor
/*@RedisHash(value = "refreshToken", timeToLive = 60)*/
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String refreshToken;

    @NotBlank
    private String userEmail;

    public RefreshToken(String refreshToken, String userEmail){
        this.refreshToken = refreshToken;
        this.userEmail = userEmail;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}
