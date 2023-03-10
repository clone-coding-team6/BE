package com.sparta.instagramclonebe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InstagramCloneBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstagramCloneBeApplication.class, args);
    }

}
