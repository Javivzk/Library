package com.svalero.library.service;

import com.svalero.library.domain.Library;
import com.svalero.library.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService{

    @Autowired
    private LibraryRepository libraryRepository;
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
}
