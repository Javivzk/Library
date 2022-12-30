package com.svalero.library.service;

import com.svalero.library.domain.User;
import com.svalero.library.repository.RentRepository;
import com.svalero.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByCode(String code) {
        return userRepository.findByCode(code);
    }
}
