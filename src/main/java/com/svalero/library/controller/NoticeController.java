package com.svalero.library.controller;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Notice;
import com.svalero.library.service.BookService;
import com.svalero.library.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @GetMapping("/notices")
    public List<Notice> getNotices() {
        return noticeService.findAll();
    }

    @GetMapping("/notices/{code}")
    public Notice getNotice(@PathVariable String code) {
        return noticeService.findByCode(code);
    }
}
