package com.svalero.library.service;

import com.svalero.library.domain.Stock;
import com.svalero.library.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService{
    @Autowired
    private StockRepository stockRepository;
    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }
}
