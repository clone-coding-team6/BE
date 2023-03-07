package com.sparta.instagramclonebe.domain.image.repository;


import com.sparta.instagramclonebe.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteAllByPostId(Long id);

    List<Image> findAllByPostId(Long id);
}