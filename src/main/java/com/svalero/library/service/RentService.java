package com.svalero.library.service;

import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Rent;

import java.util.List;

public interface RentService {

    List<Rent> findAll();
    Rent findByCode(String code);
}
