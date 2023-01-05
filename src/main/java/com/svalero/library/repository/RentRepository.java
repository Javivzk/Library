package com.svalero.library.repository;

import com.svalero.library.domain.Rent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends CrudRepository<Rent,Long> {

    List<Rent> findAll();

    List<Rent> findByIsReturned(boolean isReturned);


    Rent findByCode(String code);

}
