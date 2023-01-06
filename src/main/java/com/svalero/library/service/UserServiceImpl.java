package com.svalero.library.service;

import com.svalero.library.domain.Stock;
import com.svalero.library.domain.User;
import com.svalero.library.exception.UserNotFoundException;
import com.svalero.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByCode(String code) {
        return userRepository.findByCode(code);
    }

    @Override
    public List<User> findAllByIsMember(boolean isMember) {
        return userRepository.findByIsMember(isMember);
    }

    @Override
    public User findById(long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public User modifyUser(long id, User newUser) throws UserNotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        existingUser.setCode(newUser.getCode());

        return userRepository.save(existingUser);
    }
}
