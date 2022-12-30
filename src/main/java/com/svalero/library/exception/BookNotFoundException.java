package com.svalero.library.exception;

public class BookNotFoundException extends Exception{

    public BookNotFoundException() {
        super("Book not found");
    }
}
