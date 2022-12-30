package com.svalero.library.controller;

import com.svalero.library.domain.Rent;
import com.svalero.library.domain.Stock;
import com.svalero.library.service.RentService;
import com.svalero.library.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class StockController {

    @Autowired
    private StockService stockService;
    @GetMapping("/stocks")
    public List<Stock> getStocks() {
        return stockService.findAll();
    }


}
