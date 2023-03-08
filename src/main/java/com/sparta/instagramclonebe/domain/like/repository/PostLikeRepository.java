package com.sparta.instagramclonebe.domain.like.repository;

import com.sparta.instagramclonebe.domain.like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    Optional<PostLike> findByPostIdAndUserId(Long id, Long id1);
    void deleteByPostIdAndUserId(Long id, Long id1);
    void deleteAllByPostId(Long id);
    Long countPostLikeByPostId(Long id);
    List<PostLike> findAllByPostId(Long id);
}
