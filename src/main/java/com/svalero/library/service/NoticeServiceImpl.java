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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    @Override
    public List<Notice> findAllByHasRead(boolean hasRead) {
        return noticeRepository.findByHasRead(hasRead);
    }

    @Override
    public Notice findByCode(String code) {
        return noticeRepository.findByCode(code);
    }

    @Override
    public Notice findById(long id) throws NoticeNotFoundException {
        return noticeRepository.findById(id)
                .orElseThrow(NoticeNotFoundException::new);
    }

    @Override
    public Notice addNotice(NoticeDTO noticeDTO) throws BookNotFoundException, UserNotFoundException {
        return null;
    }

//    @Override
//    public Notice addNotice(NoticeDTO noticeDTO) throws BookNotFoundException, UserNotFoundException {
//        Notice newNotice = new Notice();
//        Optional<Book> books = bookRepository.findById(noticeDTO.getBookId());
//        Optional<User> users = userRepository.findById(noticeDTO.getUserId());

//        newNotice.setUserNotices(users);
//        newNotice.setBooks((List<Book>) books);


//        return noticeRepository.save(newNotice);
//    }

    @Override
    public void deleteNotice(long id) throws NoticeNotFoundException {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoticeNotFoundException::new);
        noticeRepository.delete(notice);
    }

    public Notice modifyNotice(long id, Notice newNotice) throws NoticeNotFoundException {
        Notice existingNotice = noticeRepository.findById(id)
                .orElseThrow(NoticeNotFoundException::new);
        existingNotice.setCode(newNotice.getCode());
        // Setear el resto de campos
        return noticeRepository.save(existingNotice);
    }
}
