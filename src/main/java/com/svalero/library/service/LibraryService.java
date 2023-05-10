package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Library;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.LibraryNotFoundException;

import java.util.List;

public interface LibraryService {

    List<Library> findAllLibraries();
    Library addLibrary(Library library);
    void deleteLibrary(long libraryId);
    Library findById(long id) throws LibraryNotFoundException;

    Library modifyLibrary(long libraryId, Library newLibrary) throws LibraryNotFoundException;


}
