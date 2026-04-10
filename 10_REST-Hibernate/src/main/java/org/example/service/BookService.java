package org.example.service;

import org.example.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    List<Book> findByCabinetId(Long cabinetId);
    List<Book> searchByTitle(String titleQuery);
    void deleteById(Long id);
}