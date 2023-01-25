package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Stock;
import com.svalero.library.domain.User;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<User> findAll();
    List<User> findByCode(String code);
    List<User> findAllByIsMember(boolean isMember);


    User findById(long id) throws UserNotFoundException;

    User addUser(User user);
    void deleteUser(long id) throws UserNotFoundException;
    User modifyUser(long id, User newUser) throws UserNotFoundException;

    User patchUser(long id, String name) throws UserNotFoundException;

}
