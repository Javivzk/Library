package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Notice;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.NoticeNotFoundException;
import org.aspectj.weaver.ast.Not;

import java.util.List;

public interface NoticeService {

    List<Notice> findAll();
    List<Notice> findAllByHasRead(boolean hasRead);

    Notice findByCode(String code);

    Notice findById(long id) throws NoticeNotFoundException;

    Notice addNotice(Notice notice);
    void deleteNotice(long id) throws NoticeNotFoundException;
    Notice modifyNotice(long id, Notice newNotice) throws NoticeNotFoundException;
}
