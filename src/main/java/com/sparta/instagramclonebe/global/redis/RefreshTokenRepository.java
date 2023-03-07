package com.sparta.instagramclonebe.global.redis;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findAllByUserEmail(String userEmail);
}
