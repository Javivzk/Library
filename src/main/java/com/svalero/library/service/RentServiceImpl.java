package com.svalero.library.service;

import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Rent;
import com.svalero.library.repository.NoticeRepository;
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
}
