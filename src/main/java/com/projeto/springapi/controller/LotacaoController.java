package com.projeto.springapi.controller;

import com.projeto.springapi.dto.LotacaoDTO;
import com.projeto.springapi.service.LotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lotacoes")
public class LotacaoController {

    @Autowired
    private LotacaoService lotacaoService;

    @GetMapping
    public ResponseEntity<Page<LotacaoDTO>> getAllLotacoes(Pageable pageable) {
        return ResponseEntity.ok(lotacaoService.getAllLotacoes(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LotacaoDTO> getLotacaoById(@PathVariable Long id) {
        return ResponseEntity.ok(lotacaoService.getLotacaoById(id));
    }

    @PostMapping
    public ResponseEntity<LotacaoDTO> createLotacao(@RequestBody LotacaoDTO lotacaoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lotacaoService.createLotacao(lotacaoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LotacaoDTO> updateLotacao(@PathVariable Long id, @RequestBody LotacaoDTO lotacaoDTO) {
        return ResponseEntity.ok(lotacaoService.updateLotacao(id, lotacaoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLotacao(@PathVariable Long id) {
        lotacaoService.deleteLotacao(id);
        return ResponseEntity.noContent().build();
    }
}
