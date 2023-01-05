package com.svalero.library.service;

import com.svalero.library.domain.Rent;
import com.svalero.library.exception.RentNotFoundException;
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
    public List<Rent> findAllByIsReturned(boolean isReturned) {
        return rentRepository.findByIsReturned(isReturned);
    }


    @Override
    public Rent findByCode(String code) {
        return rentRepository.findByCode(code);
    }

    @Override
    public Rent findById(long id) throws RentNotFoundException {
        return rentRepository.findById(id)
                .orElseThrow(RentNotFoundException::new);
    }

    @Override
    public Rent addRent(Rent rent) {
        return rentRepository.save(rent);
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
