package com.svalero.library.service;

import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Rent;
import com.svalero.library.domain.Stock;
import com.svalero.library.exception.NoticeNotFoundException;
import com.svalero.library.exception.RentNotFoundException;
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

    Stock patchStock(long id, int quantity) throws StockNotFoundException;

}
