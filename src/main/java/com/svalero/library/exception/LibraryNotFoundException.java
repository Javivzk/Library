package com.svalero.library.exception;

public class LibraryNotFoundException extends Exception{

    public LibraryNotFoundException() {
        super("Library not found");
    }

    public LibraryNotFoundException(String message) {
        super(message);
    }
}
