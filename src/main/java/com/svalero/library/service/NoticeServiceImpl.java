package com.svalero.library.service;

import com.svalero.library.domain.Notice;
import com.svalero.library.exception.NoticeNotFoundException;
import com.svalero.library.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

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
    public Notice addNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

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
