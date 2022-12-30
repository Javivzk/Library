package com.svalero.library.controller;

import com.svalero.library.domain.Rent;
import com.svalero.library.domain.User;
import com.svalero.library.service.RentService;
import com.svalero.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{code}")
    public User getUser(@PathVariable String code) {
        return userService.findByCode(code);
    }
}
