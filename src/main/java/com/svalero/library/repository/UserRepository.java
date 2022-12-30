package com.svalero.library.repository;

import com.svalero.library.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findAll();

    User findByCode(String code);

}
