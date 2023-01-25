package com.svalero.library.controller;

import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Rent;
import com.svalero.library.domain.Stock;
import com.svalero.library.domain.dto.RentDTO;
import com.svalero.library.exception.*;
import com.svalero.library.service.RentService;
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
public class RentController {

    @Autowired
    private RentService rentService;

    private  final Logger logger = LoggerFactory.getLogger(RentController.class);


    @GetMapping("/rents")
    public ResponseEntity<List<Rent>> getRents(@RequestParam Map<String, String> data) {
        logger.info("GET Rents");
        if (data.isEmpty()) {
            logger.info("END GET Rents");
            return ResponseEntity.ok(rentService.findAll());
        }else {
            if (data.containsKey("code")) {
                List<Rent> rents = rentService.findByCode(data.get("code"));
                logger.info("END GET Rents");
                return ResponseEntity.ok(rents);
            }else if(data.containsKey("returned")){
                if (data.get("returned").equals("true")){
                    List<Rent> rents = rentService.findAllByIsReturned(Boolean.TRUE);
                    logger.info("END GET Rents");
                    return ResponseEntity.ok(rents);
                }else if (data.get("returned").equals("false")){
                    List<Rent> rents = rentService.findAllByIsReturned(Boolean.FALSE);
                    logger.info("END GET Rents");
                    return ResponseEntity.ok(rents);
                }else {
                    logger.error("BAD REQUEST");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        logger.error("BAD REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/rents/{id}")
    public ResponseEntity<Rent> getRent(@PathVariable long id) throws RentNotFoundException {
        logger.info("GET Rents");
        Rent rent = rentService.findById(id);
        logger.info("END GET Rents");
        return ResponseEntity.ok(rent);
    }

    @PostMapping("/rents")
    public ResponseEntity<Rent> addRent(@Valid @RequestBody RentDTO rentDTO) throws UserNotFoundException, BookNotFoundException {
        logger.info("POST Rents");
        Rent newRent = rentService.addRent(rentDTO);
        logger.info("END POST Rents");
        return ResponseEntity.status(HttpStatus.CREATED).body(newRent);
    }

    @DeleteMapping("/rents/{id}")
    public ResponseEntity<Void> deleteRent(@PathVariable long id) throws RentNotFoundException {
        logger.info("DELETE Rents");
        rentService.deleteRent(id);
        logger.info("END DELETE Rents");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/rents/{id}")
    public ResponseEntity<Rent> modifyRent(@PathVariable long id,@RequestBody Rent rent) throws RentNotFoundException{
        logger.info("PUT Rents");
        Rent newRent = rentService.modifyRent(id,rent);
        logger.info("END PUT Rents");
        return ResponseEntity.status(HttpStatus.OK).body(newRent);
    }

    @PatchMapping("/rents/{id}")
    public ResponseEntity<Rent> patchRent(@PathVariable long id,@RequestBody boolean isReturned) throws RentNotFoundException {
        logger.error("PATCH Rent");
        Rent newRent = rentService.patchRent(id,isReturned);
        logger.error("END PATCH Rent");
        return ResponseEntity.status(HttpStatus.OK).body(newRent);
    }

    @ExceptionHandler(RentNotFoundException.class)
    public ResponseEntity<?> handleRentNotFoundException(RentNotFoundException rnfe){
        logger.error("Rent not found");
        ErrorMessage errorMessage = new ErrorMessage(404, rnfe.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(MethodArgumentNotValidException manve) {
        logger.error("Incorrect Data");
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
