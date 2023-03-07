package com.sparta.instagramclonebe.domain.image.entity;

import com.sparta.instagramclonebe.domain.comment.dto.CommentRequestDto;
import com.sparta.instagramclonebe.domain.comment.entity.Comment;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.global.util.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uploadPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Builder
    private Image(String uploadPath, User user, Post post) {
        this.uploadPath = uploadPath;
        this.user = user;
        this.post = post;
    }

    public static Image of(String uploadPath, User user, Post post){
        return Image.builder()
                .uploadPath(uploadPath)
                .user(user)
                .post(post)
                .build();
    }

}