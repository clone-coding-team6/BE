package com.sparta.instagramclonebe.domain.user.repository;

import com.sparta.instagramclonebe.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByNickname(String nickname);
}
