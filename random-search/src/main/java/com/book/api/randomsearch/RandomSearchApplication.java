package com.book.api.randomsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RandomSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(RandomSearchApplication.class, args);
    }
}
