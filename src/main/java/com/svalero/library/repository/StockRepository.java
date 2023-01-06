package com.svalero.library.repository;

import com.svalero.library.domain.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<Stock,Long> {

    List<Stock> findAll();

    List<Stock> findByCode(String code);
    List<Stock> findByIsAvailable(boolean isAvailable);


}
