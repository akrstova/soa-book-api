package com.book.api.genre.controllers;

import com.book.api.genre.model.Genre;
import com.book.api.genre.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/genres", produces = "application/json")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<List<Genre>> findAll() {
        List<Genre> genreList = genreService.findAll();
        return new ResponseEntity<>(genreList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Genre> findOne(@PathVariable("id") Long id) {
        Genre genre = genreService.findOne(id);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<Void> createGenre(@RequestBody String name) {
        genreService.createGenre(name);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody String name) {
        Genre genre = genreService.updateGenre(id, name);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id){
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
