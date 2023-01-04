package com.svalero.library.exception;

public class NoticeNotFoundException extends Exception {

    public NoticeNotFoundException() {
        super("Notice not found");
    }

    public NoticeNotFoundException(String message) {
        super(message);
    }
}
