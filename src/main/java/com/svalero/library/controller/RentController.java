package com.svalero.library.controller;

import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Rent;
import com.svalero.library.domain.Stock;
import com.svalero.library.domain.dto.RentDTO;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.ErrorMessage;
import com.svalero.library.exception.RentNotFoundException;
import com.svalero.library.exception.UserNotFoundException;
import com.svalero.library.service.RentService;
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

    //TODO filtrar por bookId
    @GetMapping("/rents")
    public ResponseEntity<List<Rent>> getRents(@RequestParam Map<String, String> data) {
        if (data.isEmpty()) {
            return ResponseEntity.ok(rentService.findAll());
        }else {
            if (data.containsKey("code")) {
                List<Rent> rents = rentService.findByCode(data.get("code"));
                return ResponseEntity.ok(rents);
            }else if(data.containsKey("returned")){
                if (data.get("returned").equals("true")){
                    List<Rent> rents = rentService.findAllByIsReturned(Boolean.TRUE);
                    return ResponseEntity.ok(rents);
                }else if (data.get("returned").equals("false")){
                    List<Rent> rents = rentService.findAllByIsReturned(Boolean.FALSE);
                    return ResponseEntity.ok(rents);
                }else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/rents/{id}")
    public ResponseEntity<Rent> getRent(@PathVariable long id) throws RentNotFoundException {
        Rent rent = rentService.findById(id);
        return ResponseEntity.ok(rent);
    }

    @PostMapping("/rents")
    public ResponseEntity<Rent> addRent(@Valid @RequestBody RentDTO rentDTO) throws UserNotFoundException, BookNotFoundException {
        Rent newRent = rentService.addRent(rentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRent);
    }

    @DeleteMapping("/rents/{id}")
    public ResponseEntity<Void> deleteRent(@PathVariable long id) throws RentNotFoundException {
        rentService.deleteRent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/rents/{id}")
    public ResponseEntity<Rent> modifyRent(@PathVariable long id,@RequestBody Rent rent) throws RentNotFoundException{
        Rent newRent = rentService.modifyRent(id,rent);
        return ResponseEntity.status(HttpStatus.OK).body(newRent);
    }

    @ExceptionHandler(RentNotFoundException.class)
    public ResponseEntity<?> handleRentNotFoundException(RentNotFoundException rnfe){
        ErrorMessage errorMessage = new ErrorMessage(404, rnfe.getMessage());
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
