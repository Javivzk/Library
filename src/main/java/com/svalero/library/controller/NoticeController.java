package com.svalero.library.controller;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Rent;
import com.svalero.library.domain.dto.NoticeDTO;
import com.svalero.library.domain.dto.RentDTO;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.ErrorMessage;
import com.svalero.library.exception.NoticeNotFoundException;
import com.svalero.library.exception.UserNotFoundException;
import com.svalero.library.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getNotices(@RequestParam Map<String, String> data) {
        if (data.isEmpty()) {
            return ResponseEntity.ok(noticeService.findAll());
        }else {
            if (data.containsKey("titleNotice")) {
                List<Notice> notices = noticeService.findByTitleNotice(data.get("titleNotice"));
                return ResponseEntity.ok(notices);
            }else if(data.containsKey("hasRead")){
                if (data.get("hasRead").equals("true")){
                    List<Notice> notices = noticeService.findAllByHasRead(Boolean.TRUE);
                    return ResponseEntity.ok(notices);
                }else if (data.get("hasRead").equals("false")){
                    List<Notice> notices = noticeService.findAllByHasRead(Boolean.FALSE);
                    return ResponseEntity.ok(notices);
                }else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/notices/{id}")
    public ResponseEntity<Notice> getNotice(@PathVariable long id) throws NoticeNotFoundException {
        Notice notice = noticeService.findById(id);
        return ResponseEntity.ok(notice);
    }

    @PostMapping("/notices")
    public ResponseEntity<Notice> addNotice(@Valid @RequestBody NoticeDTO noticeDTO) throws BookNotFoundException, UserNotFoundException {
        Notice newNotice = noticeService.addNotice(noticeDTO);
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

    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<?> handleNoticeNotFoundException(NoticeNotFoundException nnfe){
        ErrorMessage errorMessage = new ErrorMessage(404, nnfe.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ErrorMessage badRequestErrorMessage = new ErrorMessage(400, "Bad Request", errors);
        return new ResponseEntity<>(badRequestErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
