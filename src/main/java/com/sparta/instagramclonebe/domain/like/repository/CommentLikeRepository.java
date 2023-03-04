package com.sparta.instagramclonebe.domain.like.repository;

import com.sparta.instagramclonebe.domain.like.entity.CommentLike;
import com.sparta.instagramclonebe.domain.like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long id, Long id1);

    void deleteByCommentIdAndUserId(Long id, Long id1);
}
