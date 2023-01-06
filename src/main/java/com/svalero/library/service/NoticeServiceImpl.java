package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Rent;
import com.svalero.library.domain.User;
import com.svalero.library.domain.dto.NoticeDTO;
import com.svalero.library.domain.dto.RentDTO;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.NoticeNotFoundException;
import com.svalero.library.exception.UserNotFoundException;
import com.svalero.library.repository.BookRepository;
import com.svalero.library.repository.NoticeRepository;
import com.svalero.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);

    @Override
    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    @Override
    public List<Notice> findAllByHasRead(boolean hasRead) {
        logger.info("Notice state: " + hasRead);
        return noticeRepository.findByHasRead(hasRead);
    }

    @Override
    public Notice findByCode(String code) {
        logger.info("Notice Code: " + code);
        return noticeRepository.findByCode(code);
    }

    @Override
    public List<Notice> findByTitleNotice(String titleNotice) {
        logger.info("Title Notice: " + titleNotice);
        return noticeRepository.findByTitleNotice(titleNotice);
    }


    @Override
    public Notice findById(long id) throws NoticeNotFoundException {
        logger.info("Notice Id: " + id);
        return noticeRepository.findById(id)
                .orElseThrow(NoticeNotFoundException::new);
    }

    @Override
    public Notice addNotice(NoticeDTO noticeDTO) throws BookNotFoundException, UserNotFoundException {
        logger.info("Added Notice: " + noticeDTO);
        Notice newNotice = new Notice();

        Book books;
        User users;

        books = bookRepository.findById(noticeDTO.getBookId()).orElseThrow(BookNotFoundException::new);
        users = userRepository.findById(noticeDTO.getBookId()).orElseThrow(UserNotFoundException::new);


        newNotice.setCode(noticeDTO.getCode());
        newNotice.setTitleNotice(noticeDTO.getTitleNotice());
        newNotice.setDescription(noticeDTO.getDescription());
        newNotice.setRating(noticeDTO.getRating());
        newNotice.setHasRead(noticeDTO.getHasRead());
        newNotice.setUserNotices(users);
        newNotice.setBooks(Collections.singletonList(books));


        return noticeRepository.save(newNotice);
    }

    @Override
    public void deleteNotice(long id) throws NoticeNotFoundException {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoticeNotFoundException::new);
        logger.info("Notice deleted: " + id);
        noticeRepository.delete(notice);
    }

    public Notice modifyNotice(long id, Notice newNotice) throws NoticeNotFoundException {
        Notice existingNotice = noticeRepository.findById(id)
                .orElseThrow(NoticeNotFoundException::new);
        logger.info("Notice to modify: " + existingNotice);
        existingNotice.setCode(newNotice.getCode());
        // Setear el resto de campos
        logger.info("Notice Modified: " + newNotice);
        return noticeRepository.save(existingNotice);
    }
}
