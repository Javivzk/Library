package com.svalero.library.controller;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Library;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.ErrorMessage;
import com.svalero.library.exception.LibraryNotFoundException;
import com.svalero.library.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LibraryController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    LibraryService libraryService;

    @GetMapping(value = "/libraries")
    public ResponseEntity<List<Library>> getLibraries() {
        List<Library> libraries = libraryService.findAllLibraries();
        return new ResponseEntity<>(libraries, HttpStatus.OK);
    }

    @PostMapping(value = "/libraries")
    public ResponseEntity<Library> addLibrary(@RequestBody Library library) {
        Library newLibrary = libraryService.addLibrary(library);
        return new ResponseEntity<>(newLibrary, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/library/{libraryId}")
    public ResponseEntity<?> deleteLibrary(@PathVariable long libraryId) {
        libraryService.deleteLibrary(libraryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/library/{id}")
    public ResponseEntity<Library> getLibrary(@PathVariable long id) throws LibraryNotFoundException, NumberFormatException{
        logger.info("GET Library");
        Library library = libraryService.findById(id);
        logger.info("END GET Library");
        return ResponseEntity.ok(library);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        e.printStackTrace();
        logger.error("Internal Error " + e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LibraryNotFoundException.class)
    public ResponseEntity<?> handleBookNotFoundException(LibraryNotFoundException lnfe){
        logger.error("Library not found ");
        ErrorMessage errorMessage = new ErrorMessage(404, lnfe.getMessage());
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

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorMessage> handleNumberFormatException(NumberFormatException exception) {
        ErrorMessage errorMessage = new ErrorMessage(400, "Formato de parametro no valido");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
