package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Rent;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentServiceImpl implements RentService {
    @Autowired
    private RentRepository rentRepository;

    @Override
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    @Override
    public Rent findByCode(String code) {
        return rentRepository.findByCode(code);
    }

    @Override
    public Book findById(long id) throws BookNotFoundException {
        return null;
    }

    @Override
    public Book addRent(Rent rent) {
        return null;
    }

    @Override
    public void deleteRent(long id) throws BookNotFoundException {

    }

    @Override
    public Book modifyRent(long id, Rent newRent) throws BookNotFoundException {
        return null;
    }
}
