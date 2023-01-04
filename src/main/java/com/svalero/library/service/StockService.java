package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Stock;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.StockNotFoundException;

import java.util.List;

public interface StockService {

    List<Stock> findAll();
    Stock findById(long id) throws StockNotFoundException;

    List<Stock> findAllByHasStock(boolean hasStock);

    Book addBook(Book book);
    void deleteBook(long id) throws BookNotFoundException;
    Book modifyBook(long id, Book newBook) throws BookNotFoundException;

    List<Stock> findByBook(long id);
}
