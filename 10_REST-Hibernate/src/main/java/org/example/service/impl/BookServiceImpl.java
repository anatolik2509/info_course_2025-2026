package org.example.service.impl;

import org.example.entity.Book;
import org.example.repository.BookRepository;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("bookServiceEntityManager")
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findByCabinetId(Long cabinetId) {
        return bookRepository.findByCabinetId(cabinetId);
    }

    @Override
    public List<Book> searchByTitle(String titleQuery) {
        return bookRepository.searchByTitle(titleQuery);
    }

    @Override
    public List<Book> searchByTitleCriteria(String titleQuery) {
        return bookRepository.searchByTitleCriteria(titleQuery);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
