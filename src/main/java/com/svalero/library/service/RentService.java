package com.svalero.library.service;

import com.svalero.library.domain.*;
import com.svalero.library.domain.dto.RentDTO;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.NoticeNotFoundException;
import com.svalero.library.exception.RentNotFoundException;
import com.svalero.library.exception.UserNotFoundException;

import java.util.List;

public interface RentService {

    List<Rent> findAll();

    List<Rent> findAllByIsReturned(boolean isReturned);

    List<Rent> findByCode(String code);
    List<Rent> findByBook(String bookId);

    List<Rent> findByUser(User user);


    Rent findById(long id) throws RentNotFoundException;

    Rent addRent(RentDTO rentDTO) throws BookNotFoundException, UserNotFoundException;

    void deleteRent(long id) throws RentNotFoundException;

    Rent modifyRent(long id, Rent newRent) throws RentNotFoundException;

    Rent patchRent(long id, boolean isReturned) throws RentNotFoundException;

}
