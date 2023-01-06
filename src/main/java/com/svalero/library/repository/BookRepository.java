package com.svalero.library.repository;

import com.svalero.library.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {

    List<Book> findAll();

    List<Book> findByTitle(String title);
    List<Book> findByHasStock(boolean hasStock);
    Book findByCode(String code);
}
