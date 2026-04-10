package org.example.service.impl;

import org.example.entity.Cabinet;
import org.example.repository.CabinetRepository;
import org.example.service.CabinetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("cabinetServiceEntityManager")
public class CabinetServiceImpl implements CabinetService {

    private final CabinetRepository cabinetRepository;

    @Autowired
    public CabinetServiceImpl(CabinetRepository cabinetRepository) {
        this.cabinetRepository = cabinetRepository;
    }

    @Override
    public Cabinet save(Cabinet cabinet) {
        return cabinetRepository.save(cabinet);
    }

    @Override
    public Optional<Cabinet> findById(Long id) {
        return cabinetRepository.findById(id);
    }

    @Override
    public List<Cabinet> findAll() {
        return cabinetRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        cabinetRepository.deleteById(id);
    }
}
