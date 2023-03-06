package com.sparta.instagramclonebe.global.util.s3;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.instagramclonebe.domain.image.entity.Image;
import com.sparta.instagramclonebe.domain.image.repository.ImageRepository;
import com.sparta.instagramclonebe.domain.post.entity.Post;
import com.sparta.instagramclonebe.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3 S3Client;
    private final ImageRepository imageRepository;
    public void upload(List<MultipartFile> multipartFilelist, String dirName, Post post , User user) throws IOException {

    // 이미지 S3 업로드 + DB 업로드
    public void upload(List<MultipartFile> multipartFilelist, String dirName, Post post , User user) throws IOException {

        for (MultipartFile multipartFile : multipartFilelist){
            if (multipartFile != null){
                File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("파일 전환 실패"));
                Image image = new Image(upload(uploadFile, dirName), user, post);
                imageRepository.save(image);
            }
        }
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID(); // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        S3Client.putObject(new PutObjectRequest(bucketName, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return S3Client.getUrl(bucketName, fileName).toString();
    }


    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(multipartFile.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public String getThumbnailPath(String path) {
        return S3Client.getUrl(bucketName, path).toString();
    }

    public void deleteFile(String fileName){
        DeleteObjectRequest request = new DeleteObjectRequest(bucketName, fileName);
        S3Client.deleteObject(request);
    }

}