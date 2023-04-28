package com.svalero.library.service;

import com.svalero.library.domain.Book;
import com.svalero.library.domain.Library;
import com.svalero.library.exception.BookNotFoundException;
import com.svalero.library.exception.LibraryNotFoundException;
import com.svalero.library.repository.LibraryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService{

    @Autowired
    private LibraryRepository libraryRepository;

    private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    @Override
    public List<Library> findAllLibraries() {
        return libraryRepository.findAll();
    }

    @Override
    public Library addLibrary(Library library) {
        return libraryRepository.save(library);
    }

    @Override
    public void deleteLibrary(long libraryId) {
        libraryRepository.deleteById(libraryId);
    }

    @Override
    public Library findById(long id) throws LibraryNotFoundException {
        logger.info("Library id: " + id);
        return libraryRepository.findById(id)
                .orElseThrow(LibraryNotFoundException::new);
    }
}
