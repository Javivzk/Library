package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Rent;
import com.svalero.library.domain.User;
import com.svalero.library.domain.dto.RentDTO;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.RentNotFoundException;
import com.svalero.library.exception.UserNotFoundException;
import com.svalero.library.repository.BookRepository;
import com.svalero.library.repository.RentRepository;
import com.svalero.library.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RentServiceImpl implements RentService {
    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    @Override
    public List<Rent> findAllByIsReturned(boolean isReturned) {
        return rentRepository.findByIsReturned(isReturned);
    }


    @Override
    public Rent findByCode(String code) {
        return rentRepository.findByCode(code);
    }

    @Override
    public List<Rent> findByBook(Book book) {
        return rentRepository.findByBook(book);
    }

    @Override
    public List<Rent> findByUser(User user) {
        return rentRepository.findByUser(user);
    }

    @Override
    public Rent findById(long id) throws RentNotFoundException {
        return rentRepository.findById(id)
                .orElseThrow(RentNotFoundException::new);
    }


    @Override
    public Rent addRent(RentDTO rentDTO) throws BookNotFoundException, UserNotFoundException {
        Rent newRent = new Rent();
        Book book;
        User user;

        book = bookRepository.findById(rentDTO.getBookId()).orElseThrow(BookNotFoundException::new);
        user = userRepository.findById(rentDTO.getBookId()).orElseThrow(UserNotFoundException::new);

        newRent.setCode(rentDTO.getCode());
        newRent.setReturned(rentDTO.isReturned());
        newRent.setTotalPrice(rentDTO.getTotalPrice());
        newRent.setUser(Collections.singletonList(user));
        newRent.setBook(Collections.singletonList(book));


        return rentRepository.save(newRent);
    }

    @Override
    public void deleteRent(long id) throws RentNotFoundException {
        Rent rent = rentRepository.findById(id)
                .orElseThrow(RentNotFoundException::new);
        rentRepository.delete(rent);
    }

    public Rent modifyRent(long id, Rent newRent) throws RentNotFoundException {
        Rent existingRent = rentRepository.findById(id)
                .orElseThrow(RentNotFoundException::new);
        existingRent.setCode(newRent.getCode());
        // Setear el resto de campos
        return rentRepository.save(existingRent);
    }
}
