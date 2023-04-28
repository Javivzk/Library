package com.svalero.library.service;

import com.svalero.library.domain.Library;

import java.util.List;

public interface LibraryService {

    List<Library> findAllLibraries();
    Library addLibrary(Library library);
    void deleteLibrary(long libraryId);
}
