package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Notice;

import java.util.List;

public interface NoticeService {

    List<Notice> findAll();
    Notice findByCode(String code);
}
