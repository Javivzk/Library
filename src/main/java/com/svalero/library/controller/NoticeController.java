package com.svalero.library.controller;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Notice;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.NoticeNotFoundException;
import com.svalero.library.service.BookService;
import com.svalero.library.service.NoticeService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getNotices(@RequestParam(name = "read", defaultValue = "") String hasRead) {
        if (hasRead.equals("")) {
            return ResponseEntity.ok(noticeService.findAll());
        }else {
            boolean notice = Boolean.parseBoolean(hasRead);
            return ResponseEntity.ok(noticeService.findAllByHasRead(notice));
        }
    }

    @GetMapping("/notices/{id}")
    public ResponseEntity<Notice> getNotice(@PathVariable long id) throws NoticeNotFoundException {
        Notice notice = noticeService.findById(id);
        return ResponseEntity.ok(notice);
    }

    @PostMapping("/notices")
    public ResponseEntity<Notice> addNotice(@Valid @RequestBody Notice notice) {
        Notice newNotice = noticeService.addNotice(notice);
        return ResponseEntity.status(HttpStatus.CREATED).body(newNotice);
    }

    @DeleteMapping("/notices/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable long id) throws NoticeNotFoundException {
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/notices/{id}")
    public ResponseEntity<Notice> modifyNotice(@PathVariable long id,@RequestBody Notice notice) throws NoticeNotFoundException{
        Notice newNotice = noticeService.modifyNotice(id,notice);
        return ResponseEntity.status(HttpStatus.OK).body(newNotice);
    }
}
