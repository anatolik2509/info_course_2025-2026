package org.example.repository.impl;

import org.example.entity.Cabinet;
import org.example.repository.CabinetRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CabinetRepositoryImpl implements CabinetRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cabinet save(Cabinet cabinet) {
        if (cabinet.getId() == null) {
            entityManager.persist(cabinet);
            return cabinet;
        } else {
            return entityManager.merge(cabinet);
        }
    }

    @Override
    public Optional<Cabinet> findById(Long id) {
        Cabinet cabinet = entityManager.find(Cabinet.class, id);
        return Optional.ofNullable(cabinet);
    }

    @Override
    public List<Cabinet> findAll() {
        TypedQuery<Cabinet> query = entityManager.createQuery("SELECT c FROM Cabinet c", Cabinet.class);
        return query.getResultList();
    }

    @Override
    public void delete(Cabinet cabinet) {
        if (entityManager.contains(cabinet)) {
            entityManager.remove(cabinet);
        } else {
            entityManager.remove(entityManager.merge(cabinet));
        }
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }
}