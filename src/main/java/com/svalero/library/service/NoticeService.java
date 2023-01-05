package com.svalero.library.service;

import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Rent;
import com.svalero.library.domain.dto.NoticeDTO;
import com.svalero.library.domain.dto.RentDTO;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.NoticeNotFoundException;
import com.svalero.library.exception.UserNotFoundException;

import java.util.List;

public interface NoticeService {

    List<Notice> findAll();
    List<Notice> findAllByHasRead(boolean hasRead);

    Notice findByCode(String code);

    Notice findById(long id) throws NoticeNotFoundException;

    Notice addNotice(NoticeDTO noticeDTO) throws BookNotFoundException, UserNotFoundException;
    void deleteNotice(long id) throws NoticeNotFoundException;
    Notice modifyNotice(long id, Notice newNotice) throws NoticeNotFoundException;
}
