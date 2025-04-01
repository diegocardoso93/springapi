package com.projeto.springapi.controller;

import com.projeto.springapi.dto.CidadeDTO;
import com.projeto.springapi.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cidades")
@Tag(name = "Cidades", description = "Gerenciamento de cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @Operation(summary = "Cria uma nova cidade")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "201", description = "Cidade criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @PostMapping
    public ResponseEntity<CidadeDTO> createCidade(@RequestBody CidadeDTO cidadeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cidadeService.createCidade(cidadeDTO));
    }

    @Operation(summary = "Busca uma cidade por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cidade encontrada"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada")})
    @GetMapping("/{id}")
    public ResponseEntity<CidadeDTO> getCidadeById(@PathVariable Long id) {
        return ResponseEntity.ok(cidadeService.getCidadeById(id));
    }

    @Operation(summary = "Lista todas as cidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cidades encontrada")})
    @GetMapping
    public ResponseEntity<Page<CidadeDTO>> getAllCidades(Pageable pageable) {
        return ResponseEntity.ok(cidadeService.getAllCidades(pageable));
    }

    @Operation(summary = "Atualiza uma cidade existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @PutMapping("/{id}")
    public ResponseEntity<CidadeDTO> updateCidade(@PathVariable Long id,
            @RequestBody CidadeDTO cidadeDTO) {
        return ResponseEntity.ok(cidadeService.updateCidade(id, cidadeDTO));
    }

    @Operation(summary = "Exclui uma cidade por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cidade excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCidade(@PathVariable Long id) {
        cidadeService.deleteCidade(id);
        return ResponseEntity.noContent().build();
    }
}
