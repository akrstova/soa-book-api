package com.book.api.randomsearch.controllers;

import com.book.api.randomsearch.model.*;
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

import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/random-search", produces = "application/json")
public class RandomSearchController {

    private static final String HTTP_PREFIX = "http://";
    private static final String BOOKS_ENDPOINT = ":8080/books/";
    private static final String AUTHORS_ENDPOINT = ":8080/authors/";
    private static final String GENRES_ENDPOINT = ":8080/genres/";

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    @Autowired
    public RandomSearchController(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @RequestMapping(method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<List<BookDto>> getRandomBooks() throws EntityNotFoundException {
        Random randomServiceNumber = new Random();

        List<ServiceInstance> bookServices = discoveryClient.getInstances("book");
        List<ServiceInstance> genreServices = discoveryClient.getInstances("genre");
        List<ServiceInstance> authorServices = discoveryClient.getInstances("author");

        EurekaDiscoveryClient.EurekaServiceInstance bookServiceInstance =
                (EurekaDiscoveryClient.EurekaServiceInstance) bookServices
                        .get(randomServiceNumber.nextInt(bookServices.size()));

        EurekaDiscoveryClient.EurekaServiceInstance genreServiceInstance =
                (EurekaDiscoveryClient.EurekaServiceInstance) bookServices
                        .get(randomServiceNumber.nextInt(genreServices.size()));

        EurekaDiscoveryClient.EurekaServiceInstance authorServiceInstance =
                (EurekaDiscoveryClient.EurekaServiceInstance) bookServices
                        .get(randomServiceNumber.nextInt(authorServices.size()));


        Random randomPageNumber = new Random(10);

        Map<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("page", randomPageNumber.nextInt());
        uriVariables.put("size", 3);    // prefixed number of books per response

        String bookServiceInstanceIp = bookServiceInstance.getInstanceInfo().getIPAddr();
        ResponseEntity<List<Book>> books = this.restTemplate
                .getForObject(HTTP_PREFIX + bookServiceInstanceIp + BOOKS_ENDPOINT, ResponseEntity.class, uriVariables);

        String genreServiceInstanceIp = genreServiceInstance.getInstanceInfo().getIPAddr();
        List<GenreDto> genres = getGenres(genreServiceInstanceIp, books.getBody());

        String authorServiceInstanceIp = authorServiceInstance.getInstanceInfo().getIPAddr();
        List<AuthorDto> authors = getAuthors(authorServiceInstanceIp, books.getBody());


        List<BookDto> resultBooks = new ArrayList<>();

        for (Book book : books.getBody()) {
            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setName(book.getName());
            bookDto.setNumPages(book.getNumPages());
            bookDto.setYearPublished(book.getYearPublished());
            bookDto.setDescription(book.getDescription());
            GenreDto genreDto = genres.stream()
                    .filter(g -> g.getId().equals(book.getGenreId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Non existent genre"));
            bookDto.setGenre(genreDto);

            AuthorDto authorDto = authors.stream()
                    .filter(a -> a.getId().equals(book.getAuthorId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Non existent author"));
            bookDto.setAuthor(authorDto);

            resultBooks.add(bookDto);
        }

        return ResponseEntity.ok(resultBooks);
    }

    @SuppressWarnings("unchecked")
    private List<AuthorDto> getAuthors(String authorServiceInstanceIp, List<Book> books) {
        List<Long> authorIds = books
                .stream()
                .map(Book::getAuthorId)
                .collect(toList());

        List<AuthorDto> authors = new ArrayList<>();

        for (Long id : authorIds) {
            ResponseEntity<AuthorDto> author = this.restTemplate
                    .getForObject(HTTP_PREFIX + authorServiceInstanceIp + AUTHORS_ENDPOINT + id, ResponseEntity.class);
            authors.add(author.getBody());
        }

        return authors;
    }

    @SuppressWarnings("unchecked")
    private List<GenreDto> getGenres(String genreServiceInstanceIp, List<Book> books) {
        List<Long> genreIds = books
                .stream()
                .map(Book::getGenreId)
                .collect(toList());

        List<GenreDto> genres = new ArrayList<>();

        for (Long id : genreIds) {
            ResponseEntity<GenreDto> genre = this.restTemplate
                    .getForObject(HTTP_PREFIX + genreServiceInstanceIp + GENRES_ENDPOINT + id, ResponseEntity.class);
            genres.add(genre.getBody());
        }
        return genres;
    }
}
