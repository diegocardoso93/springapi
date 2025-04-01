package com.projeto.springapi.controller;

import com.projeto.springapi.dto.LotacaoDTO;
import com.projeto.springapi.service.LotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lotacoes")
@Tag(name = "Lotações", description = "Operações relacionadas a lotações")
public class LotacaoController {

    @Autowired
    private LotacaoService lotacaoService;

    @GetMapping
    @Operation(summary = "Listar todas as lotações",
            description = "Retorna uma lista paginada de todas as lotações.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Lista de lotações retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    public ResponseEntity<Page<LotacaoDTO>> getAllLotacoes(Pageable pageable) {
        return ResponseEntity.ok(lotacaoService.getAllLotacoes(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma lotação por ID",
            description = "Retorna uma lotação específica com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lotação encontrada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LotacaoDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Lotação não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    public ResponseEntity<LotacaoDTO> getLotacaoById(@Parameter(
            description = "ID da lotação a ser buscada", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(lotacaoService.getLotacaoById(id));
    }

    @PostMapping
    @Operation(summary = "Criar uma nova lotação",
            description = "Cria uma nova lotação com os dados fornecidos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da lotação a serem criados", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LotacaoDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Lotação criada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LotacaoDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    public ResponseEntity<LotacaoDTO> createLotacao(@RequestBody LotacaoDTO lotacaoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lotacaoService.createLotacao(lotacaoDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma lotação existente",
            description = "Atualiza os dados de uma lotação existente com base no ID fornecido.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados para a lotação", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LotacaoDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Lotação atualizada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LotacaoDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
                    @ApiResponse(responseCode = "404", description = "Lotação não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    public ResponseEntity<LotacaoDTO> updateLotacao(
            @Parameter(description = "ID da lotação a ser atualizada",
                    required = true) @PathVariable Long id,
            @RequestBody LotacaoDTO lotacaoDTO) {
        return ResponseEntity.ok(lotacaoService.updateLotacao(id, lotacaoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma lotação",
            description = "Exclui uma lotação com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Lotação excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Lotação não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    public ResponseEntity<Void> deleteLotacao(@Parameter(
            description = "ID da lotação a ser excluída", required = true) @PathVariable Long id) {
        lotacaoService.deleteLotacao(id);
        return ResponseEntity.noContent().build();
    }
}
