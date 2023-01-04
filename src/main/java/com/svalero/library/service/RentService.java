package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Rent;
import com.svalero.library.exception.BookNotFoundException;

import java.util.List;

public interface RentService {

    List<Rent> findAll();
    Rent findByCode(String code);

    Book findById(long id) throws BookNotFoundException;

    Book addRent(Rent rent);

    void deleteRent(long id) throws BookNotFoundException;

    Book modifyRent(long id, Rent newRent) throws BookNotFoundException;
}
