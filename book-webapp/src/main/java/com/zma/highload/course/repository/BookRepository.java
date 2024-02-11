package com.zma.highload.course.repository;

import com.zma.highload.course.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
