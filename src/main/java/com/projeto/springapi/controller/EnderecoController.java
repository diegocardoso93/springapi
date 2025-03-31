package com.projeto.springapi.controller;

import com.projeto.springapi.dto.EnderecoDTO;
import com.projeto.springapi.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<Endereco> createEndereco(@RequestBody EnderecoRequest enderecoRequest) {
        Endereco createdEndereco = enderecoService.createEndereco(enderecoRequest);
        return new ResponseEntity<>(createdEndereco, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable Integer id) {
        Endereco endereco = enderecoService.getEnderecoById(id);
        return ResponseEntity.ok(endereco);
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> createEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.createEndereco(enderecoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> updateEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(enderecoService.updateEndereco(id, enderecoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        enderecoService.deleteEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
