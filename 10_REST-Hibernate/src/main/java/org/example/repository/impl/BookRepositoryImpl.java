package org.example.repository.impl;

import org.example.entity.Book;
import org.example.repository.BookRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = entityManager.find(Book.class, id);
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> findByCabinetId(Long cabinetId) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.cabinet.id = :cabinetId", Book.class);
        query.setParameter("cabinetId", cabinetId);
        return query.getResultList();
    }

    @Override
    public List<Book> searchByTitle(String titleQuery) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(:titleQuery)", Book.class);
        query.setParameter("titleQuery", "%" + titleQuery + "%");
        return query.getResultList();
    }

    @Override
    public void delete(Book book) {
        if (entityManager.contains(book)) {
            entityManager.remove(book);
        } else {
            entityManager.remove(entityManager.merge(book));
        }
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }
}