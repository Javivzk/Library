package com.svalero.library.service;

import com.svalero.library.domain.*;
import com.svalero.library.domain.dto.RentDTO;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.NoticeNotFoundException;
import com.svalero.library.exception.RentNotFoundException;
import com.svalero.library.exception.UserNotFoundException;
import com.svalero.library.repository.BookRepository;
import com.svalero.library.repository.RentRepository;
import com.svalero.library.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(RentServiceImpl.class);


    @Override
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    @Override
    public List<Rent> findAllByIsReturned(boolean isReturned) {
        logger.info("Rent State: " + isReturned);
        return rentRepository.findByIsReturned(isReturned);
    }


    @Override
    public List<Rent> findByCode(String code) {
        logger.info("Rent code: " + code);
        return rentRepository.findByCode(code);
    }

    @Override
    public List<Rent> findByBook(String bookId) {
        logger.info("Rent BookId: " + bookId);
        return rentRepository.findByBook(bookId);
    }

    @Override
    public List<Rent> findByUser(User user) {
        logger.info("Rent User: " + user);
        return rentRepository.findByUser(user);
    }

    @Override
    public Rent findById(long id) throws RentNotFoundException {
        logger.info("Rent Id: " + id);
        return rentRepository.findById(id)
                .orElseThrow(RentNotFoundException::new);
    }


    @Override
    public Rent addRent(RentDTO rentDTO) throws BookNotFoundException, UserNotFoundException {
        logger.info("Added Rent: " + rentDTO);

        Rent newRent = new Rent();
        Book book;
        User user;

        book = bookRepository.findById(rentDTO.getBookId()).orElseThrow(BookNotFoundException::new);
        user = userRepository.findById(rentDTO.getBookId()).orElseThrow(UserNotFoundException::new);

        newRent.setCode(rentDTO.getCode());
        newRent.setReturned(rentDTO.isReturned());
        newRent.setStartRent(rentDTO.getStartRent());
        newRent.setEndRent(rentDTO.getEndRent());
        newRent.setTotalPrice(rentDTO.getTotalPrice());
        newRent.setUser(Collections.singletonList(user));
        newRent.setBook(Collections.singletonList(book));


        return rentRepository.save(newRent);
    }

    @Override
    public void deleteRent(long id) throws RentNotFoundException {
        Rent rent = rentRepository.findById(id)
                .orElseThrow(RentNotFoundException::new);
        logger.info("Deleted Rent: " + id);
        rentRepository.delete(rent);
    }

    public Rent modifyRent(long id, Rent newRent) throws RentNotFoundException {
        Rent existingRent = rentRepository.findById(id)
                .orElseThrow(RentNotFoundException::new);
        logger.info("Rent to modify: " + existingRent);

        existingRent.setCode(newRent.getCode());
        existingRent.setStartRent(newRent.getStartRent());
        existingRent.setEndRent(newRent.getEndRent());
        existingRent.setReturned(newRent.isReturned());
        existingRent.setTotalPrice(newRent.getTotalPrice());

        // Setear el resto de campos
        logger.info("Rent modified: " + newRent);
        return rentRepository.save(existingRent);
    }

    @Override
    public Rent patchRent(long id, boolean isReturned) throws RentNotFoundException {
        Rent existingRent = rentRepository.findById(id)
                .orElseThrow(RentNotFoundException::new);
        logger.info("Rent to patch isReturned: " + existingRent);
        existingRent.setReturned(isReturned);
        logger.info("isReturned patched: " + isReturned);

        // Setear el resto de campos
        return rentRepository.save(existingRent);

    }
}
