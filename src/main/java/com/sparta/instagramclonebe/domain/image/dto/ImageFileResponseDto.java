package com.sparta.instagramclonebe.domain.image.dto;

import com.sparta.instagramclonebe.domain.image.entity.Image;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ImageFileResponseDto {
    private Long id;                // 이미지 파일 id
    private String fileName;        // 이미지 파일명
    private String uploadPath;      // 이미지 파일 경로
    private Long fileSize;          // 이미지 파일 크기
    private LocalDateTime createdAt;

    public ImageFileResponseDto(Image image) {
        this.id = image.getId();
        this.fileName = image.getFileName();
        this.uploadPath = image.getUploadPath();
        this.fileSize = image.getFileSize();
   }
}