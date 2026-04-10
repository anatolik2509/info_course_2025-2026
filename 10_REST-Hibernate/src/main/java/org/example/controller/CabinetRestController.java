package org.example.controller;

import org.example.dto.CabinetDTO;
import org.example.dto.CreateCabinetRequest;
import org.example.entity.Cabinet;
import org.example.service.CabinetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cabinets")
public class CabinetRestController {

    private final CabinetService cabinetService;

    @Autowired
    public CabinetRestController(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    @GetMapping
    public ResponseEntity<List<CabinetDTO>> getAllCabinets() {
        List<CabinetDTO> cabinets = cabinetService.findAll().stream()
                .map(CabinetDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cabinets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CabinetDTO> getCabinetById(@PathVariable Long id) {
        return cabinetService.findById(id)
                .map(cabinet -> ResponseEntity.ok(CabinetDTO.fromEntity(cabinet)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CabinetDTO> createCabinet(@RequestBody CreateCabinetRequest request) {
        Cabinet cabinet = new Cabinet();
        cabinet.setName(request.getName());
        cabinet.setLocation(request.getLocation());
        cabinet.setCapacity(request.getCapacity());
        cabinet.setBooks(new ArrayList<>());

        Cabinet savedCabinet = cabinetService.save(cabinet);
        return ResponseEntity.status(HttpStatus.CREATED).body(CabinetDTO.fromEntity(savedCabinet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCabinet(@PathVariable Long id) {
        cabinetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
