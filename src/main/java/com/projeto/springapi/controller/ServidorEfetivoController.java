package com.projeto.springapi.controller;

import com.projeto.springapi.dto.ServidorEfetivoDTO;
import com.projeto.springapi.service.ServidorEfetivoService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servidores-efetivos")
public class ServidorEfetivoController {

    @Autowired
    private ServidorEfetivoService servidorEfetivoService;

    // @Operation(summary = "Get a paginated list of products")
    // @GetMapping("/products")
    // public Page<Product> getProducts(
    // @Parameter(description = "Page number", required = false)
    // @RequestParam(defaultValue = "0") int page, // Default to page 0 if not
    // provided

    // @Parameter(description = "Page size", required = false)
    // @RequestParam(defaultValue = "10") int size, // Default to size 10 if not
    // provided

    // @Parameter(description = "Sorting order (field,asc|desc)", required = false)
    // @RequestParam(defaultValue = "id,asc") String sort // Default to sorting by
    // id in ascending order
    // ) {
    // Pageable pageable = PageRequest.of(page, size, Sort.by(sort.split(",")));
    // return productService.findAll(pageable);
    // }

    @GetMapping
    public ResponseEntity<Page<ServidorEfetivoDTO>> getAllServidoresEfetivos(Pageable pageable) {
        return ResponseEntity.ok(servidorEfetivoService.getAllServidoresEfetivos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServidorEfetivoDTO> getServidorEfetivoById(@PathVariable Long id) {
        return ResponseEntity.ok(servidorEfetivoService.getServidorEfetivoById(id));
    }

    @PostMapping
    public ResponseEntity<ServidorEfetivoDTO> createServidorEfetivo(
            @RequestBody ServidorEfetivoDTO servidorEfetivoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servidorEfetivoService.createServidorEfetivo(servidorEfetivoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServidorEfetivoDTO> updateServidorEfetivo(@PathVariable Long id,
            @RequestBody ServidorEfetivoDTO servidorEfetivoDTO) {
        return ResponseEntity.ok(servidorEfetivoService.updateServidorEfetivo(id, servidorEfetivoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServidorEfetivo(@PathVariable Long id) {
        servidorEfetivoService.deleteServidorEfetivo(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/unidade/{unidadeId}")
    // public ResponseEntity<List<ServidorEfetivoDTO>>
    // getServidoresEfetivosByUnidade(@PathVariable Long unidadeId) {
    // return
    // ResponseEntity.ok(servidorEfetivoService.getServidoresEfetivosByUnidade(unidadeId));
    // }

    // @GetMapping("/endereco-funcional")
    // public ResponseEntity<List<ServidorEfetivoDTO>>
    // getServidoresEfetivosByNome(@RequestParam("nome") String nome) {
    // return
    // ResponseEntity.ok(servidorEfetivoService.getServidoresEfetivosByNome(nome));
    // }
}
