package org.example.service.impl;

import org.example.entity.Book;
import org.example.repository.jpa.BookJpaRepository;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("bookServiceJpa")
@Primary
public class BookServiceJpaImpl implements BookService {

    private final BookJpaRepository bookJpaRepository;

    @Autowired
    public BookServiceJpaImpl(BookJpaRepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    @Override
    public Book save(Book book) {
        return bookJpaRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookJpaRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookJpaRepository.findAll();
    }

    @Override
    public List<Book> findByCabinetId(Long cabinetId) {
        return bookJpaRepository.findByCabinetId(cabinetId);
    }

    @Override
    public List<Book> searchByTitle(String titleQuery) {
        return bookJpaRepository.searchByTitle(titleQuery);
    }

    @Override
    public List<Book> searchByTitleCriteria(String titleQuery) {
        // JpaRepository не поддерживает CriteriaBuilder напрямую,
        // используем существующий метод searchByTitle
        return bookJpaRepository.searchByTitle(titleQuery);
    }

    @Override
    public void deleteById(Long id) {
        bookJpaRepository.deleteById(id);
    }
}
