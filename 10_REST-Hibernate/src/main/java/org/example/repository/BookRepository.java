package org.example.repository;

import org.example.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    List<Book> findByCabinetId(Long cabinetId);
    List<Book> searchByTitle(String titleQuery);
    List<Book> searchByTitleCriteria(String titleQuery);
    void delete(Book book);
    void deleteById(Long id);
}