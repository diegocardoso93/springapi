package com.projeto.springapi.controller;

import com.projeto.springapi.dto.UnidadeDTO;
import com.projeto.springapi.model.Unidade;
import com.projeto.springapi.dto.EnderecoDTO;
import com.projeto.springapi.service.UnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/unidades")
@Tag(name = "Unidades", description = "Operações relacionadas a unidades")
public class UnidadeController {

    @Autowired
    private UnidadeService unidadeService;

    @Operation(summary = "Lista todas as unidades com paginação",
            description = "Retorna uma lista paginada de todas as unidades.", tags = {"Unidades"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de unidades retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "204", description = "Nenhuma unidade encontrada",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<Page<UnidadeDTO>> getAllUnidades(@Parameter(
            description = "Objeto de paginação para controlar o número de elementos por página, a página atual e a ordenação",
            schema = @Schema(implementation = Pageable.class)) Pageable pageable) {
        Page<UnidadeDTO> unidades = unidadeService.getAllUnidades(pageable);
        return unidades.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(unidades);
    }

    @Operation(summary = "Busca uma unidade por ID",
            description = "Retorna os detalhes de uma unidade específica com base no seu ID.",
            tags = {"Unidades"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<UnidadeDTO> getUnidadeById(@Parameter(
            description = "ID da unidade a ser buscada", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(unidadeService.getUnidadeById(id));
    }

    @Operation(summary = "Cria uma nova unidade", description = "Cria um novo registro de unidade.",
            tags = {"Unidades"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unidade criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<UnidadeDTO> createUnidade(@Parameter(
            description = "Dados da unidade a serem criados", required = true, schema = @Schema(
                    implementation = UnidadeDTO.class)) @RequestBody UnidadeDTO unidadeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(unidadeService.createUnidade(unidadeDTO));
    }

    @Operation(summary = "Atualiza uma unidade existente",
            description = "Atualiza os dados de uma unidade específica com base no seu ID.",
            tags = {"Unidades"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<UnidadeDTO> updateUnidade(
            @Parameter(description = "ID da unidade a ser atualizada",
                    required = true) @PathVariable Long id,
            @Parameter(description = "Novos dados da unidade", required = true, schema = @Schema(
                    implementation = UnidadeDTO.class)) @RequestBody UnidadeDTO unidadeDTO) {
        return ResponseEntity.ok(unidadeService.updateUnidade(id, unidadeDTO));
    }

    @Operation(summary = "Exclui uma unidade por ID",
            description = "Remove uma unidade específica com base no seu ID.", tags = {"Unidades"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Unidade excluída com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnidade(@Parameter(
            description = "ID da unidade a ser excluída", required = true) @PathVariable Long id) {
        unidadeService.deleteUnidade(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona um endereço a uma unidade existente",
            description = "Adiciona um novo endereço à unidade especificada.",
            tags = {"Unidades", "Endereços"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço adicionado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Unidade.class))),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content)})
    @PostMapping("/{id}/enderecos")
    public ResponseEntity<UnidadeDTO> addEnderecoToUnidade(
            @Parameter(description = "ID da unidade para adicionar o endereço",
                    required = true) @PathVariable Long id,
            @Parameter(description = "Dados do endereço a serem adicionados", required = true,
                    schema = @Schema(
                            implementation = EnderecoDTO.class)) @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(unidadeService.addEnderecoToUnidade(id, enderecoDTO));
    }
}
