package com.svalero.library.service;

import com.svalero.library.domain.Rent;
import com.svalero.library.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findByCode(String code);
}
