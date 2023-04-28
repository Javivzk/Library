package com.svalero.library.controller;

import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Rent;
import com.svalero.library.domain.Stock;
import com.svalero.library.exception.ErrorMessage;
import com.svalero.library.exception.RentNotFoundException;
import com.svalero.library.exception.StockNotFoundException;
import com.svalero.library.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private  final Logger logger = LoggerFactory.getLogger(StockController.class);

    @GetMapping("/stocks")
    public ResponseEntity<List<Stock>> getStocks(@RequestParam Map<String, String> data) {
        logger.info("GET Stocks");
        if (data.isEmpty()) {
            logger.info("END GET Stocks");
            return ResponseEntity.ok(stockService.findAll());
        }else {
            if (data.containsKey("code")) {
                List<Stock> stocks = stockService.findByCode(data.get("code"));
                logger.info("END GET Stocks");
                return ResponseEntity.ok(stocks);
            }else if(data.containsKey("isAvailable")){
                if (data.get("isAvailable").equals("true")){
                    List<Stock> stocks= stockService.findAllByIsAvailable(Boolean.TRUE);
                    logger.info("END GET Stocks");
                    return ResponseEntity.ok(stocks);
                }else if (data.get("isAvailable").equals("false")){
                    List<Stock> stocks = stockService.findAllByIsAvailable(Boolean.FALSE);
                    logger.info("END GET Stocks");
                    return ResponseEntity.ok(stocks);
                }else {
                    logger.error("BAD REQUEST");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        logger.error("BAD REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<Stock> getStock(@PathVariable long id) throws StockNotFoundException {
        logger.info("GET Stocks");
        Stock stock = stockService.findById(id);
        logger.info("END GET Stocks");
        return ResponseEntity.ok(stock);
    }

    @PostMapping("/stocks")
    public ResponseEntity<Stock> addStock(@Valid @RequestBody Stock stock) {
        logger.info("POST Stocks");
        Stock newStock = stockService.addStock(stock);
        logger.info("END POST Stocks");
        return ResponseEntity.status(HttpStatus.CREATED).body(newStock);
    }

    @DeleteMapping("/stock/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable long id) throws StockNotFoundException {
        logger.info("DELETE Stocks");
        stockService.deleteStock(id);
        logger.info("End DELETE Stocks");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/stock/{id}")
    public ResponseEntity<Stock> modifyStock(@PathVariable long id,@RequestBody Stock stock) throws StockNotFoundException{
        logger.info("PUT Stocks");
        Stock newStock = stockService.modifyStock(id,stock);
        logger.info("END PUT Stocks");
        return ResponseEntity.status(HttpStatus.OK).body(newStock);
    }

    @PatchMapping("/stock/{id}")
    public ResponseEntity<Stock> patchStock(@PathVariable long id, @RequestBody int quantity) throws StockNotFoundException {
        logger.error("PATCH Stock");
        Stock newStock = stockService.patchStock(id,quantity);
        logger.error("END PATCH Stock");
        return ResponseEntity.status(HttpStatus.OK).body(newStock);
    }


    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<?> handleRentNotFoundException(StockNotFoundException snfe){
        logger.error("Stock not found");
        ErrorMessage errorMessage = new ErrorMessage(404, snfe.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(MethodArgumentNotValidException manve) {
        logger.error("Incorrect data");
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
        logger.error("Internal Error " + e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
