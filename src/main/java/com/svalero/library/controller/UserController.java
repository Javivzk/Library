package com.svalero.library.controller;

import com.svalero.library.domain.User;
import com.svalero.library.exception.ErrorMessage;
import com.svalero.library.exception.UserNotFoundException;
import com.svalero.library.service.UserService;
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
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(name = "member", defaultValue = "") String isMember) {
        if (isMember.equals("")) {
            return ResponseEntity.ok(userService.findAll());
        }else {
            boolean member = Boolean.parseBoolean(isMember);
            return ResponseEntity.ok(userService.findAllByIsMember(member));
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) throws UserNotFoundException {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User newUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable long id, @RequestBody User user) throws UserNotFoundException{
        User modifiedUser = userService.modifyUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedUser);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException unfe){
        ErrorMessage errorMessage = new ErrorMessage(404, unfe.getMessage());
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
