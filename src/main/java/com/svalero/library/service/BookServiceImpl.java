package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findAllByHasStock(boolean hasStock) {
        logger.info("Book Stock: " + hasStock);
        return bookRepository.findByHasStock(hasStock);
    }

    @Override
    public Book findById(long id) throws BookNotFoundException {
        logger.info("Book id: " + id);
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public Book findByCode(String code) {
        return null;
    }

    @Override
    public List<Book> findByTitle(String title) {
        logger.info("Book Title: " + title);
        return bookRepository.findByTitle(title);
    }

    @Override
    public Book addBook(Book book) {
        logger.info("Added Book: " + book);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        logger.info("Deleted Book: " + id);
        bookRepository.delete(book);
    }

    @Override
    public Book modifyBook(long id, Book newBook) throws BookNotFoundException {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        logger.info("Book to modify: " + existingBook);
        existingBook.setCode(newBook.getCode());
        logger.info("Book modified: " + newBook);

        // Setear el resto de campos
        return bookRepository.save(existingBook);
    }
}
