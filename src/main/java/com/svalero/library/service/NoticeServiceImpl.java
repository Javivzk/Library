package com.svalero.library.service;

import com.svalero.library.domain.Notice;
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
    public Notice findByCode(String code) {
        return noticeRepository.findByCode(code);
    }
}
