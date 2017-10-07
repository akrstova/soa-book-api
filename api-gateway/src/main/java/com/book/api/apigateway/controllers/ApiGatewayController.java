package com.book.api.apigateway.controllers;

import com.book.api.randomsearch.model.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ApiGatewayController {
    private static final String HTTP_PREFIX = "http://";
    private static final String BOOKS_ENDPOINT = ":8080/books/";
    private static final String AUTHORS_ENDPOINT = ":8080/authors/";
    private static final String GENRES_ENDPOINT = ":8080/genres/";
    private static final String RATINGS_ENDPOINT = ":8080/ratings";
    private static final String RANDOM_ENDPOINT = ":8080/random-search";

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    @Autowired
    public ApiGatewayController(DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate();
        this.discoveryClient = discoveryClient;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getRandomBooks() {
        Random randomServiceNumber = new Random();

        List<ServiceInstance> randomSearchServices = discoveryClient.getInstances("random-search");
        EurekaDiscoveryClient.EurekaServiceInstance randomSearchInstance =
                (EurekaDiscoveryClient.EurekaServiceInstance) randomSearchServices.get(randomServiceNumber.nextInt(randomSearchServices.size()));

        Random randomPageNumber = new Random(10);
        Map<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("page", randomPageNumber.nextInt());
        uriVariables.put("size", 5);

        return this.restTemplate
                .getForObject(HTTP_PREFIX + randomSearchInstance + RANDOM_ENDPOINT, ResponseEntity.class, uriVariables);
    }


}
