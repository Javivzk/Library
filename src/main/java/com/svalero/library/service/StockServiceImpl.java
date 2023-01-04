package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Stock;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.StockNotFoundException;
import com.svalero.library.repository.BookRepository;
import com.svalero.library.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(long id) throws StockNotFoundException {
        logger.info("ID Stock: " + id);
        return stockRepository.findById(id).orElseThrow(StockNotFoundException::new);
    }

    @Override
    public List<Stock> findAllByHasStock(boolean hasStock) {
        return null;
    }

    @Override
    public Book addBook(Book book) {
        return null;
    }

    @Override
    public void deleteBook(long id) throws BookNotFoundException {

    }

    @Override
    public Book modifyBook(long id, Book newBook) throws BookNotFoundException {
        return null;
    }

    @Override
    public List<Stock> findByBook(long id) {
        return null;
    }
}
