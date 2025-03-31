package com.projeto.springapi.controller;

import com.projeto.springapi.dto.UnidadeDTO;
import com.projeto.springapi.service.UnidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/unidades")
public class UnidadeController {

    @Autowired
    private UnidadeService unidadeService;

    @GetMapping
    public ResponseEntity<Page<UnidadeDTO>> getAllUnidades(Pageable pageable) {
        return ResponseEntity.ok(unidadeService.getAllUnidades(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeDTO> getUnidadeById(@PathVariable Long id) {
        return ResponseEntity.ok(unidadeService.getUnidadeById(id));
    }

    @PostMapping
    public ResponseEntity<UnidadeDTO> createUnidade(@RequestBody UnidadeDTO unidadeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadeService.createUnidade(unidadeDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeDTO> updateUnidade(@PathVariable Long id, @RequestBody UnidadeDTO unidadeDTO) {
        return ResponseEntity.ok(unidadeService.updateUnidade(id, unidadeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnidade(@PathVariable Long id) {
        unidadeService.deleteUnidade(id);
        return ResponseEntity.noContent().build();
    }
}
