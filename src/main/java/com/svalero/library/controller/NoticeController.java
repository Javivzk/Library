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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private  final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getNotices(@RequestParam Map<String, String> data) {
        logger.info("GET Notices");
        if (data.isEmpty()) {
            logger.info("END GET Notices");
            return ResponseEntity.ok(noticeService.findAll());
        }else {
            if (data.containsKey("titleNotice")) {
                List<Notice> notices = noticeService.findByTitleNotice(data.get("titleNotice"));
                logger.info("END GET Notices");
                return ResponseEntity.ok(notices);
            }else if(data.containsKey("hasRead")){
                if (data.get("hasRead").equals("true")){
                    List<Notice> notices = noticeService.findAllByHasRead(Boolean.TRUE);
                    logger.info("END GET Notices");
                    return ResponseEntity.ok(notices);
                }else if (data.get("hasRead").equals("false")){
                    List<Notice> notices = noticeService.findAllByHasRead(Boolean.FALSE);
                    logger.info("END GET Notices");
                    return ResponseEntity.ok(notices);
                }else {
                    logger.error("BAD REQUEST");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        logger.error("BAD REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/notice/{id}")
    public ResponseEntity<Notice> getNotice(@PathVariable long id) throws NoticeNotFoundException {
        logger.info("GET Notice");
        Notice notice = noticeService.findById(id);
        logger.info("END GET Notice");
        return ResponseEntity.ok(notice);
    }

    @PostMapping("/notices")
    public ResponseEntity<Notice> addNotice(@Valid @RequestBody Notice notice) throws BookNotFoundException, UserNotFoundException {
        logger.info("POST Notices");
        Notice newNotice = noticeService.addNotice(notice);
        logger.info("END POST Notices");
        return ResponseEntity.status(HttpStatus.CREATED).body(newNotice);
    }

    @DeleteMapping("/notice/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable long id) throws NoticeNotFoundException {
        logger.info("DELETE Notices");
        noticeService.deleteNotice(id);
        logger.info("END DELETE Notices");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/notice/{id}")
    public ResponseEntity<Notice> modifyNotice(@PathVariable long id,@RequestBody Notice notice) throws NoticeNotFoundException{
        logger.info("PUT Notices");
        Notice newNotice = noticeService.modifyNotice(id,notice);
        logger.info("END PUT Notices");
        return ResponseEntity.status(HttpStatus.OK).body(newNotice);
    }

    @PatchMapping("/notice/{id}")
    public ResponseEntity<Notice> patchNotice(@PathVariable long id,@RequestBody boolean hasRead) throws NoticeNotFoundException{
        logger.error("PATCH Notice");
        Notice newNotice = noticeService.patchNotice(id,hasRead);
        logger.error("END PATCH Notice");
        return ResponseEntity.status(HttpStatus.OK).body(newNotice);
    }

    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<?> handleNoticeNotFoundException(NoticeNotFoundException nnfe){
        logger.error("Notice not found");
        ErrorMessage errorMessage = new ErrorMessage(404, nnfe.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(MethodArgumentNotValidException manve) {
        logger.error("Incorrect Data");
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
        logger.error("Internal Error " + e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
