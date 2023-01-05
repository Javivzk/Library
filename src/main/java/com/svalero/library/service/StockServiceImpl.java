package com.svalero.library.service;

import com.svalero.library.domain.Stock;
import com.svalero.library.exception.StockNotFoundException;
import com.svalero.library.repository.BookRepository;
import com.svalero.library.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(long id) throws StockNotFoundException {
        return stockRepository.findById(id)
                .orElseThrow(StockNotFoundException::new);
    }

    @Override
    public List<Stock> findAllByIsAvailable(boolean isAvailable) {
        return stockRepository.findByIsAvailable(isAvailable);
    }

    @Override
    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public void deleteStock(long id) throws StockNotFoundException {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(StockNotFoundException::new);
        stockRepository.delete(stock);
    }

    public Stock modifyStock(long id, Stock newStock) throws StockNotFoundException {
        Stock existingStock = stockRepository.findById(id)
                .orElseThrow(StockNotFoundException::new);
        existingStock.setCode(newStock.getCode());
        // Setear el resto de campos
        return stockRepository.save(existingStock);
    }

    @Override
    public List<Stock> findByBook(long id) {
        return null;
    }
}
