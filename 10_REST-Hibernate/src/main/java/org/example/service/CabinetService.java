package org.example.service;

import org.example.entity.Cabinet;

import java.util.List;
import java.util.Optional;

public interface CabinetService {
    Cabinet save(Cabinet cabinet);
    Optional<Cabinet> findById(Long id);
    List<Cabinet> findAll();
    void deleteById(Long id);
}
