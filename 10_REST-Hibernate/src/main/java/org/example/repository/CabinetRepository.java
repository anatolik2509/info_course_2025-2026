package org.example.repository;

import org.example.entity.Cabinet;

import java.util.List;
import java.util.Optional;

public interface CabinetRepository {
    Cabinet save(Cabinet cabinet);
    Optional<Cabinet> findById(Long id);
    List<Cabinet> findAll();
    void delete(Cabinet cabinet);
    void deleteById(Long id);
}