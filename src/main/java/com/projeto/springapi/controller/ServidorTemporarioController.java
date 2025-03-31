package com.projeto.springapi.controller;

import com.projeto.springapi.dto.ServidorTemporarioDTO;
import com.projeto.springapi.service.ServidorTemporarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servidores-temporarios")
public class ServidorTemporarioController {

    @Autowired
    private ServidorTemporarioService servidorTemporarioService;

    @GetMapping
    public ResponseEntity<Page<ServidorTemporarioDTO>> getAllServidoresTemporarios(Pageable pageable) {
        return ResponseEntity.ok(servidorTemporarioService.getAllServidoresTemporarios(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServidorTemporarioDTO> getServidorTemporarioById(@PathVariable Long id) {
        return ResponseEntity.ok(servidorTemporarioService.getServidorTemporarioById(id));
    }

    @PostMapping
    public ResponseEntity<ServidorTemporarioDTO> createServidorTemporario(
            @RequestBody ServidorTemporarioDTO servidorTemporarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servidorTemporarioService.createServidorTemporario(servidorTemporarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServidorTemporarioDTO> updateServidorTemporario(@PathVariable Long id,
            @RequestBody ServidorTemporarioDTO servidorTemporarioDTO) {
        return ResponseEntity.ok(servidorTemporarioService.updateServidorTemporario(id, servidorTemporarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServidorTemporario(@PathVariable Long id) {
        servidorTemporarioService.deleteServidorTemporario(id);
        return ResponseEntity.noContent().build();
    }
}
