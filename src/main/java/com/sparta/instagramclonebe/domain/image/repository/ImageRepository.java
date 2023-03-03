package com.sparta.instagramclonebe.domain.image.repository;


import com.sparta.instagramclonebe.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {


}