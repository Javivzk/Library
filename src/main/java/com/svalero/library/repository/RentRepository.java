package com.svalero.library.repository;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Rent;
import com.svalero.library.domain.Stock;
import com.svalero.library.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends CrudRepository<Rent,Long> {

    List<Rent> findAll();

    List<Rent> findByIsReturned(boolean isReturned);

    List<Rent> findByBook(String bookId);

    List<Rent> findByUser(User user);



    List<Rent> findByCode(String code);

}
