package com.sparta.instagramclonebe.domain.image.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageFileRequestDto {
    private String fileName;
    private String uploadPath;
    private long fileSize;

    public ImageFileRequestDto(String fileName, String uploadPath, long fileSize) {
        this.fileName = fileName;
        this.uploadPath = uploadPath;
        this.fileSize = fileSize;
    }
}