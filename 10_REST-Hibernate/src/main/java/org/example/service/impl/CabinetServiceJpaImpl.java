package org.example.service.impl;

import org.example.entity.Cabinet;
import org.example.repository.jpa.CabinetJpaRepository;
import org.example.service.CabinetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("cabinetServiceJpa")
@Primary
public class CabinetServiceJpaImpl implements CabinetService {

    private final CabinetJpaRepository cabinetJpaRepository;

    @Autowired
    public CabinetServiceJpaImpl(CabinetJpaRepository cabinetJpaRepository) {
        this.cabinetJpaRepository = cabinetJpaRepository;
    }

    @Override
    public Cabinet save(Cabinet cabinet) {
        return cabinetJpaRepository.save(cabinet);
    }

    @Override
    public Optional<Cabinet> findById(Long id) {
        return cabinetJpaRepository.findById(id);
    }

    @Override
    public List<Cabinet> findAll() {
        return cabinetJpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        cabinetJpaRepository.deleteById(id);
    }
}
