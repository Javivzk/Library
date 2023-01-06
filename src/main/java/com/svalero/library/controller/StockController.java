package com.svalero.library.controller;

import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Stock;
import com.svalero.library.exception.ErrorMessage;
import com.svalero.library.exception.StockNotFoundException;
import com.svalero.library.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;
    @GetMapping("/stocks")
    public ResponseEntity<List<Stock>> getStocks(@RequestParam Map<String, String> data) {
        if (data.isEmpty()) {
            return ResponseEntity.ok(stockService.findAll());
        }else {
            if (data.containsKey("code")) {
                List<Stock> stocks = stockService.findByCode(data.get("code"));
                return ResponseEntity.ok(stocks);
            }else if(data.containsKey("isAvailable")){
                if (data.get("isAvailable").equals("true")){
                    List<Stock> stocks= stockService.findAllByIsAvailable(Boolean.TRUE);
                    return ResponseEntity.ok(stocks);
                }else if (data.get("isAvailable").equals("false")){
                    List<Stock> stocks = stockService.findAllByIsAvailable(Boolean.FALSE);
                    return ResponseEntity.ok(stocks);
                }else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<Stock> getStock(@PathVariable long id) throws StockNotFoundException {
        Stock stock = stockService.findById(id);
        return ResponseEntity.ok(stock);
    }

    @PostMapping("/stocks")
    public ResponseEntity<Stock> addStock(@Valid @RequestBody Stock stock) {
        Stock newStock = stockService.addStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStock);
    }

    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable long id) throws StockNotFoundException {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/stocks/{id}")
    public ResponseEntity<Stock> modifyStock(@PathVariable long id,@RequestBody Stock stock) throws StockNotFoundException{
        Stock newStock = stockService.modifyStock(id,stock);
        return ResponseEntity.status(HttpStatus.OK).body(newStock);
    }

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<?> handleRentNotFoundException(StockNotFoundException snfe){
        ErrorMessage errorMessage = new ErrorMessage(404, snfe.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ErrorMessage badRequestErrorMessage = new ErrorMessage(400, "Bad Request", errors);
        return new ResponseEntity<>(badRequestErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
