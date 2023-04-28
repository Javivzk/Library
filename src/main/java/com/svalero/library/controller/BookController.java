package com.svalero.library.controller;

import com.svalero.library.domain.Book;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.ErrorMessage;
import com.svalero.library.service.BookService;
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
public class BookController {

    @Autowired
    private BookService bookService;

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks(@RequestParam Map<String, String> data) {
        logger.info("GET Books");
        if (data.isEmpty()) {
            logger.info("END GET Books");
            return ResponseEntity.ok(bookService.findAll());
        }else {
            if (data.containsKey("title")) {
                List<Book> books = bookService.findByTitle(data.get("title"));
                logger.info("END GET Books");
                return ResponseEntity.ok(books);
            }else if(data.containsKey("stock")){
                if (data.get("stock").equals("true")){
                    List<Book> books = bookService.findAllByHasStock(Boolean.TRUE);
                    logger.info("END GET Books");
                    return ResponseEntity.ok(books);
                }else if (data.get("stock").equals("false")){
                    List<Book> books = bookService.findAllByHasStock(Boolean.FALSE);
                    logger.info("END GET Books");
                    return ResponseEntity.ok(books);
                }else {
                    logger.error("BAD REQUEST");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        logger.info("GET Books: BAD REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


//        logger.info("GET Books");
//        if (data.isEmpty()) {
//            logger.info("END GET Books");
//            return ResponseEntity.ok(bookService.findAll());
//        }else {
//            if (data.containsKey("title")) {
//                List<Book> books = bookService.findByTitle(data.get("title"));
//                logger.info("END GET Books");
//                return ResponseEntity.ok(books);
//            }else if(data.containsKey("hasStock")){
//                if (data.get("hasStock").equals("true")){
//                    List<Book> books = bookService.findAllByHasStock(Boolean.TRUE);
//                    logger.info("END GET Books");
//                    return ResponseEntity.ok(books);
//                }else if (data.get("hasStock").equals("false")){
//                    List<Book> books = bookService.findAllByHasStock(Boolean.FALSE);
//                    logger.info("END GET Books");
//                    return ResponseEntity.ok(books);
//                }else {
//                    logger.error("BAD REQUEST");
//                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//                }
//            }
//        }
//        logger.info("GET Books: BAD REQUEST");
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id) throws BookNotFoundException, NumberFormatException{
        logger.info("GET Book");
        Book book = bookService.findById(id);
        logger.info("END GET Book");
        return ResponseEntity.ok(book);
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        logger.info("POST Book");
        Book newBook = bookService.addBook(book);
        logger.info("END POST Book");
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) throws BookNotFoundException {
        logger.error("DELETE Books");
        bookService.deleteBook(id);
        logger.error("END DELETE Books");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> modifyBook(@PathVariable long id, @Valid @RequestBody Book book) throws BookNotFoundException{
        logger.error("PUT Book");
        Book newBook = bookService.modifyBook(id,book);
        logger.error("END PUT Book");
        return ResponseEntity.status(HttpStatus.OK).body(newBook);
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity<Book> patchBook(@PathVariable long id,@RequestBody String author) throws BookNotFoundException{
        logger.error("PATCH Book");
        Book newBook = bookService.patchBook(id,author);
        logger.error("END PATCH Book");
        return ResponseEntity.status(HttpStatus.OK).body(newBook);
    }


    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException bnfe){
        logger.error("Book not found ");
        ErrorMessage errorMessage = new ErrorMessage(404, bnfe.getMessage());
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        logger.error("Internal Error ", e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
