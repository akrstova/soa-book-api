package com.book.api.apigateway.controllers;

import com.book.api.apigateway.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.awt.print.Book;
import java.math.BigDecimal;
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
    private static final String RATINGS_ENDPOINT = ":8080/ratings/";
    private static final String RANDOM_ENDPOINT = ":8080/random-search";
    private static final String USERS_ENDPOINT = ":8080/users/";

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

    private String getRandomBookInstanceIp() {
        Random randomServiceNumber = new Random();

        List<ServiceInstance> bookServices = discoveryClient.getInstances("books");
        EurekaDiscoveryClient.EurekaServiceInstance bookServiceInstance =
                (EurekaDiscoveryClient.EurekaServiceInstance) bookServices.get(randomServiceNumber.nextInt(bookServices.size()));

        return bookServiceInstance.getInstanceInfo().getIPAddr();
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooks(@RequestParam Integer page, @RequestParam Integer size) {
        Map<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("page", page);
        uriVariables.put("size", size);

        String bookInstanceIp = getRandomBookInstanceIp();
        return this.restTemplate.getForObject(HTTP_PREFIX + bookInstanceIp + BOOKS_ENDPOINT, ResponseEntity.class, uriVariables);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        String bookInstanceIp = getRandomBookInstanceIp();
        return this.restTemplate.getForObject(HTTP_PREFIX + bookInstanceIp + BOOKS_ENDPOINT + id, ResponseEntity.class);
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book) {
        String bookInstanceIp = getRandomBookInstanceIp();
        HttpEntity<Book> bookHttpEntity = new HttpEntity<>(book);
        return this.restTemplate.exchange(HTTP_PREFIX + bookInstanceIp + BOOKS_ENDPOINT,
                HttpMethod.POST,
                bookHttpEntity,
                Book.class);
    }

    @RequestMapping(value = "/books/search", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("query", query);

        String bookInstanceIp = getRandomBookInstanceIp();
        return this.restTemplate.getForObject(HTTP_PREFIX + bookInstanceIp + BOOKS_ENDPOINT + "search", ResponseEntity.class, uriVariables);
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getRandomBooks() {
        Random randomServiceNumber = new Random();

        List<ServiceInstance> randomSearchServices = discoveryClient.getInstances("random-search");
        EurekaDiscoveryClient.EurekaServiceInstance randomSearchInstance =
                (EurekaDiscoveryClient.EurekaServiceInstance) randomSearchServices.get(randomServiceNumber.nextInt(randomSearchServices.size()));

        String randomSearchInstanceIp = randomSearchInstance.getInstanceInfo().getIPAddr();
        return this.restTemplate
                .getForObject(HTTP_PREFIX + randomSearchInstanceIp + RANDOM_ENDPOINT, ResponseEntity.class);
    }

    private String getRandomGenreInstanceIp() {
        Random randomServiceNumber = new Random();

        List<ServiceInstance> genreServices = discoveryClient.getInstances("genres");
        EurekaDiscoveryClient.EurekaServiceInstance genreServiceInstance =
                (EurekaDiscoveryClient.EurekaServiceInstance) genreServices.get(randomServiceNumber.nextInt(genreServices.size()));

        return genreServiceInstance.getInstanceInfo().getIPAddr();
    }

    @RequestMapping(value = "/genres", method = RequestMethod.GET)
    public ResponseEntity<List<Genre>> getGenres() {
        String genreInstanceIp = getRandomGenreInstanceIp();
        return this.restTemplate.getForObject(HTTP_PREFIX + genreInstanceIp + GENRES_ENDPOINT, ResponseEntity.class);
    }

    @RequestMapping(value = "/genres/{id}", method = RequestMethod.GET)
    public ResponseEntity<Genre> getGenreById(@PathVariable("id") Long id) {
        String genreInstanceIp = getRandomGenreInstanceIp();
        return this.restTemplate.getForObject(HTTP_PREFIX + genreInstanceIp + GENRES_ENDPOINT + id, ResponseEntity.class);
    }

    private String getRandomAuthorInstanceIp() {
        Random randomServiceNumber = new Random();

        List<ServiceInstance> authorServices = discoveryClient.getInstances("authors");
        EurekaDiscoveryClient.EurekaServiceInstance authorServiceInstance =
                (EurekaDiscoveryClient.EurekaServiceInstance) authorServices.get(randomServiceNumber.nextInt(authorServices.size()));

        return authorServiceInstance.getInstanceInfo().getIPAddr();
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public ResponseEntity<List<Author>> getAuthors() {
        String authorInstanceIp = getRandomAuthorInstanceIp();
        return this.restTemplate.getForObject(HTTP_PREFIX + authorInstanceIp + AUTHORS_ENDPOINT, ResponseEntity.class);
    }

    @RequestMapping(value = "/authors/{id}", method = RequestMethod.GET)
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) {
        String authorInstanceIp = getRandomAuthorInstanceIp();
        return this.restTemplate.getForObject(HTTP_PREFIX + authorInstanceIp + AUTHORS_ENDPOINT + id, ResponseEntity.class);
    }

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    public ResponseEntity<Void> createAuthor(@RequestBody Author author) {
        String authorInstanceIp = getRandomAuthorInstanceIp();
        HttpEntity<Author> authorHttpEntity = new HttpEntity<>(author);
        return this.restTemplate.exchange(HTTP_PREFIX + authorInstanceIp + BOOKS_ENDPOINT,
                HttpMethod.POST,
                authorHttpEntity,
                Void.class);
    }

    private String getRandomRatingInstanceIp() {
        Random randomServiceNumber = new Random();

        List<ServiceInstance> ratingServices = discoveryClient.getInstances("ratings");
        EurekaDiscoveryClient.EurekaServiceInstance ratingServiceInstance =
                (EurekaDiscoveryClient.EurekaServiceInstance) ratingServices.get(randomServiceNumber.nextInt(ratingServices.size()));

        return ratingServiceInstance.getInstanceInfo().getIPAddr();
    }

    @RequestMapping(value = "/ratings/{bookId}", method = RequestMethod.GET)
    public ResponseEntity<RatingDto> getBookRating(@PathVariable Long bookId) {
        String ratingInstanceIp = getRandomRatingInstanceIp();
        return this.restTemplate.getForObject(HTTP_PREFIX + ratingInstanceIp + RATINGS_ENDPOINT + bookId, ResponseEntity.class);
    }

    @RequestMapping(value = "/ratings", method = RequestMethod.GET)
    public ResponseEntity<List<RatingDto>> searchByAverageGrade(@RequestParam Integer pageNumber,
                                                                @RequestParam Integer pageSize,
                                                                @RequestParam BigDecimal grade) {
        String ratingInstanceIp = getRandomRatingInstanceIp();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(HTTP_PREFIX + ratingInstanceIp + RATINGS_ENDPOINT)
                .queryParam("pageNumber", pageNumber)
                .queryParam("pageSize", pageSize)
                .queryParam("grade", grade);

        ResponseEntity<List<RatingDto>> response = this.restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RatingDto>>() {
                }
        );
        return response;
    }

    private String getRandomUserInstanceIp() {
        Random randomServiceNumber = new Random();

        List<ServiceInstance> userServices = discoveryClient.getInstances("users");
        EurekaDiscoveryClient.EurekaServiceInstance userServiceInstance =
                (EurekaDiscoveryClient.EurekaServiceInstance) userServices.get(randomServiceNumber.nextInt(userServices.size()));

        return userServiceInstance.getInstanceInfo().getIPAddr();
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getUsers() {
        String userInstanceIp = getRandomUserInstanceIp();
        return this.restTemplate.getForObject(HTTP_PREFIX + userInstanceIp + USERS_ENDPOINT, ResponseEntity.class);
    }
}
