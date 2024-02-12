package com.zma.highload.course.controller;

import com.zma.highload.course.model.Book;
import com.zma.highload.course.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final static Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public void createBook(@RequestBody Book book) {
        repository.save(book);
        logger.info("Book created {}", book);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable int id) {
        logger.info("Find by id={} book request", id);
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found"));
    }

    @GetMapping
    public List<Book> getAllBook() {
        logger.info("Get all books request");
        return (List<Book>) repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id) {
        logger.info("Delete by id={} book request", id);
        repository.deleteById(id);
    }
}
