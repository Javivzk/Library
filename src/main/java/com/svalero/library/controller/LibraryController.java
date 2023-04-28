package com.svalero.library.controller;

import com.svalero.library.domain.Library;
import com.svalero.library.exception.ErrorMessage;
import com.svalero.library.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        e.printStackTrace();
        logger.error("Internal Error " + e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
