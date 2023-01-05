package com.svalero.library.service;

import com.svalero.library.domain.Rent;
import com.svalero.library.exception.RentNotFoundException;

import java.util.List;

public interface RentService {

    List<Rent> findAll();

    List<Rent> findAllByIsReturned(boolean isReturned);

    Rent findByCode(String code);

    Rent findById(long id) throws RentNotFoundException;

    Rent addRent(Rent rent);

    void deleteRent(long id) throws RentNotFoundException;

    Rent modifyRent(long id, Rent newRent) throws RentNotFoundException;
}
