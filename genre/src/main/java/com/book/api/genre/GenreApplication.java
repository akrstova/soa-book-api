package com.book.api.genre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class GenreApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenreApplication.class, args);
    }
}
