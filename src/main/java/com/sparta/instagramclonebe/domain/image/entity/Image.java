package com.sparta.instagramclonebe.domain.image.entity;

import com.sparta.instagramclonebe.domain.image.dto.ImageFileRequestDto;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.global.util.Timestamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 이미지 파일 id

    @Column(nullable = false)
    private String fileName;        // 이미지 파일명

    @Column(nullable = false)
    private String uploadPath;      // 이미지 파일 경로

    @Column(nullable = false)
    private long fileSize;          // 이미지 파일 크기

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = true)
    private Post post;

    public Image(String fileName, String uploadPath, long fileSize) {
        this.fileName = fileName;
        this.uploadPath = uploadPath;
        this.fileSize = fileSize;
    }
    public Image(ImageFileRequestDto imageFileRequestDto, Post post) {
        this.fileName = imageFileRequestDto.getFileName();
        this.uploadPath = imageFileRequestDto.getUploadPath();
        this.fileSize = imageFileRequestDto.getFileSize();
        this.post = post;
    }
}