package com.svalero.library.controller;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Stock;
import com.svalero.library.domain.User;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.ErrorMessage;
import com.svalero.library.exception.UserNotFoundException;
import com.svalero.library.service.UserService;
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
public class UserController {
    @Autowired
    private UserService userService;

    private  final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam Map<String, String> data) {
        logger.info("GET Users");
        if (data.isEmpty()) {
            logger.info("END GET Users");
            return ResponseEntity.ok(userService.findAll());
        }else {
            if (data.containsKey("code")) {
                List<User> users = userService.findByCode(data.get("code"));
                logger.info("END GET Users");
                return ResponseEntity.ok(users);
            }else if(data.containsKey("member")){
                if (data.get("member").equals("true")){
                    List<User> users= userService.findAllByIsMember(Boolean.TRUE);
                    logger.info("END GET Users");
                    return ResponseEntity.ok(users);
                }else if (data.get("member").equals("false")){
                    List<User> users = userService.findAllByIsMember(Boolean.FALSE);
                    logger.info("END GET Users");
                    return ResponseEntity.ok(users);
                }else {
                    logger.error("BAD REQUEST");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        logger.error("BAD REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) throws UserNotFoundException {
        logger.info("GET Users");
        User user = userService.findById(id);
        logger.info("END GET Users");
        return ResponseEntity.ok(user);
    }


    @PostMapping("/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        logger.info("POST Users");
        User newUser = userService.addUser(user);
        logger.info("END POST Users");
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws UserNotFoundException {
        logger.info("DELETE Users");
        userService.deleteUser(id);
        logger.info("END DELETE Users");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable long id, @RequestBody User user) throws UserNotFoundException{
        logger.info("PUT Users");
        User modifiedUser = userService.modifyUser(id, user);
        logger.info("END PUT Users");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedUser);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> patchUsers(@PathVariable long id, @RequestBody String name) throws UserNotFoundException {
        logger.error("PATCH User");
        User newUser = userService.patchUser(id,name);
        logger.error("END PATCH User");
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException unfe){
        logger.error("User not found");
        ErrorMessage errorMessage = new ErrorMessage(404, unfe.getMessage());
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
        logger.error("Internal error " + e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
