package com.svalero.library.service;

import com.svalero.library.domain.Rent;
import com.svalero.library.domain.Stock;
import com.svalero.library.exception.RentNotFoundException;
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
        logger.info("Stock Id: " + id);
        return stockRepository.findById(id)
                .orElseThrow(StockNotFoundException::new);
    }

    @Override
    public List<Stock> findAllByIsAvailable(boolean isAvailable) {
        logger.info("Stock State: " + isAvailable);
        return stockRepository.findByIsAvailable(isAvailable);
    }

    @Override
    public List<Stock> findByCode(String code) {
        logger.info("Stock Code: " + code);
        return stockRepository.findByCode(code);
    }

    @Override
    public Stock addStock(Stock stock) {
        logger.info("Stock added: " + stock);
        return stockRepository.save(stock);
    }

    @Override
    public void deleteStock(long id) throws StockNotFoundException {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(StockNotFoundException::new);
        logger.info("Deleted Stock: " + id);
        stockRepository.delete(stock);
    }

    public Stock modifyStock(long id, Stock newStock) throws StockNotFoundException {
        Stock existingStock = stockRepository.findById(id)
                .orElseThrow(StockNotFoundException::new);
        logger.info("Stock to modify: " + existingStock);
        existingStock.setCode(newStock.getCode());
        existingStock.setIsAvailable(newStock.getIsAvailable());
        existingStock.setQuantity(newStock.getQuantity());


        logger.info("Stock modified: " + id);

        return stockRepository.save(existingStock);
    }

    @Override
    public Stock patchStock(long id, int quantity) throws StockNotFoundException {
        Stock existingStock = stockRepository.findById(id)
                .orElseThrow(StockNotFoundException::new);
        logger.info("Stock to patch quantity: " + existingStock);
        existingStock.setQuantity(quantity);
        logger.info("Quantity patched: " + quantity);

        // Setear el resto de campos
        return stockRepository.save(existingStock);

    }

    @Override
    public List<Stock> findByBook(long id) {
        return null;
    }
}
