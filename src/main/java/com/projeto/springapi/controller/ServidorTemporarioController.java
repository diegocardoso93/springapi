package com.projeto.springapi.controller;

import com.projeto.springapi.dto.ServidorTemporarioDTO;
import com.projeto.springapi.service.ServidorTemporarioService;
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
@RequestMapping("/api/servidores-temporarios")
@Tag(name = "Servidores Temporários", description = "Operações relacionadas a servidores temporários")
public class ServidorTemporarioController {

    @Autowired
    private ServidorTemporarioService servidorTemporarioService;

    @Operation(
            summary = "Lista todos os servidores temporários com paginação",
            description = "Retorna uma lista paginada de todos os servidores temporários.",
            tags = {"Servidores Temporários"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de servidores temporários retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum servidor temporário encontrado",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<ServidorTemporarioDTO>> getAllServidoresTemporarios(
            @Parameter(description = "Objeto de paginação para controlar o número de elementos por página, a página atual e a ordenação",
                    schema = @Schema(implementation = Pageable.class)) Pageable pageable) {
        Page<ServidorTemporarioDTO> servidores = servidorTemporarioService.getAllServidoresTemporarios(pageable);
        return servidores.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(servidores);
    }

    @Operation(
            summary = "Busca um servidor temporário por ID",
            description = "Retorna os detalhes de um servidor temporário específico com base no seu ID.",
            tags = {"Servidores Temporários"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servidor temporário encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorTemporarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Servidor temporário não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServidorTemporarioDTO> getServidorTemporarioById(
            @Parameter(description = "ID do servidor temporário a ser buscado", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(servidorTemporarioService.getServidorTemporarioById(id));
    }

    @Operation(
            summary = "Cria um novo servidor temporário",
            description = "Cria um novo registro de servidor temporário.",
            tags = {"Servidores Temporários"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Servidor temporário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorTemporarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ServidorTemporarioDTO> createServidorTemporario(
            @Parameter(description = "Dados do servidor temporário a serem criados", required = true,
                    schema = @Schema(implementation = ServidorTemporarioDTO.class)) @RequestBody ServidorTemporarioDTO servidorTemporarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servidorTemporarioService.createServidorTemporario(servidorTemporarioDTO));
    }

    @Operation(
            summary = "Atualiza um servidor temporário existente",
            description = "Atualiza os dados de um servidor temporário específico com base no seu ID.",
            tags = {"Servidores Temporários"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servidor temporário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorTemporarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Servidor temporário não encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ServidorTemporarioDTO> updateServidorTemporario(
            @Parameter(description = "ID do servidor temporário a ser atualizado", required = true) @PathVariable Long id,
            @Parameter(description = "Novos dados do servidor temporário", required = true,
                    schema = @Schema(implementation = ServidorTemporarioDTO.class)) @RequestBody ServidorTemporarioDTO servidorTemporarioDTO) {
        return ResponseEntity.ok(servidorTemporarioService.updateServidorTemporario(id, servidorTemporarioDTO));
    }

    @Operation(
            summary = "Exclui um servidor temporário por ID",
            description = "Remove um servidor temporário específico com base no seu ID.",
            tags = {"Servidores Temporários"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Servidor temporário excluído com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Servidor temporário não encontrado",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServidorTemporario(
            @Parameter(description = "ID do servidor temporário a ser excluído", required = true) @PathVariable Long id) {
        servidorTemporarioService.deleteServidorTemporario(id);
        return ResponseEntity.noContent().build();
    }
}
