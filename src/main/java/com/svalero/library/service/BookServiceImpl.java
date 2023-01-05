package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findAllByHasStock(boolean hasStock) {
        return bookRepository.findByHasStock(hasStock);
    }

    @Override
    public Book findById(long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public Book findByCode(String code) {
        return null;
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }

    @Override
    public Book modifyBook(long id, Book newBook) throws BookNotFoundException {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        existingBook.setCode(newBook.getCode());
        // Setear el resto de campos
        return bookRepository.save(existingBook);
    }
}
