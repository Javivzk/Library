package com.svalero.library.repository;

import com.svalero.library.domain.Notice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {

    List<Notice> findAll();

    Notice findByCode(String code);

}
