package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.exception.BookNotFoundException;

import java.util.List;

public interface BookService {

    List<Book> findAll();
    List<Book> findAllByHasStock(boolean hasStock);
    Book findById(long id) throws BookNotFoundException;

    Book addBook(Book book);
    void deleteBook(long id) throws BookNotFoundException;
    Book modifyBook(long id, Book newBook) throws BookNotFoundException;
}
