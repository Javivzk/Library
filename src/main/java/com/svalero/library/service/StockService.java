package com.svalero.library.service;

import com.svalero.library.domain.Stock;
import com.svalero.library.exception.StockNotFoundException;

import java.util.List;

public interface StockService {

    List<Stock> findAll();
    Stock findById(long id) throws StockNotFoundException;

    List<Stock> findAllByIsAvailable(boolean isAvailable);

    List<Stock> findByCode(String code);

    Stock addStock(Stock stock);
    void deleteStock(long id) throws StockNotFoundException;
    Stock modifyStock(long id, Stock newStock) throws StockNotFoundException;

    List<Stock> findByBook(long id);
}
