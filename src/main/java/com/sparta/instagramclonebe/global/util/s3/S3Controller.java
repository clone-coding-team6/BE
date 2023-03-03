package com.sparta.instagramclonebe.global.util.s3;

import com.sparta.instagramclonebe.domain.image.dto.ImageFileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {
    private final S3Service s3Service;
    private static String dirName = "product-img";

    // S3 이미지 업로드(+ DB 저장)
    @PostMapping("/file")
    public ResponseEntity<List<ImageFileRequestDto>> uploadFile(@RequestPart List<MultipartFile> multipartFile) {
        return ResponseEntity.ok().body(s3Service.uploadFile(multipartFile, dirName));
    }

    // S3 이미지 삭제
    @DeleteMapping("/file")
    public String deleteFile(@RequestParam String fileName) {
        s3Service.deleteFile(fileName, dirName);
        return "이미지 삭제 완료";
    }
}