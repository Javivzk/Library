package com.svalero.library.service;

import com.svalero.library.domain.Stock;
import com.svalero.library.domain.User;
import com.svalero.library.exception.UserNotFoundException;
import com.svalero.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByCode(String code) {
        logger.info("User Code: " + code);
        return userRepository.findByCode(code);
    }

    @Override
    public List<User> findAllByIsMember(boolean isMember) {
        logger.info("User State: " + isMember);
        return userRepository.findByIsMember(isMember);
    }

    @Override
    public User findById(long id) throws UserNotFoundException {
        logger.info("User Id: " + id);
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User addUser(User user) {
        logger.info("Added User: " + user);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        logger.info("Deleted User: " + id);
        userRepository.delete(user);
    }

    @Override
    public User modifyUser(long id, User newUser) throws UserNotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        logger.info("User to modify: " + existingUser);
        existingUser.setCode(newUser.getCode());

        logger.info("User modified: " + id);

        return userRepository.save(existingUser);
    }
}
