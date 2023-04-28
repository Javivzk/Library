package com.svalero.library.repository;

import com.svalero.library.domain.Library;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends CrudRepository<Library, Long> {

    List<Library> findAll();
}
