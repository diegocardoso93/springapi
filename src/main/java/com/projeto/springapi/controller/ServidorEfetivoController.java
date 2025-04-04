package com.projeto.springapi.controller;

import com.projeto.springapi.dto.ConsultaEnderecoFuncionalServidorEfetivoPorNomeDTO;
import com.projeto.springapi.dto.ConsultaServidorLotadoPorUnidadeDTO;
import com.projeto.springapi.dto.EnderecoDTO;
import com.projeto.springapi.dto.ServidorEfetivoDTO;
import com.projeto.springapi.model.ServidorEfetivo;
import com.projeto.springapi.service.ServidorEfetivoService;

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
@RequestMapping("/api/servidores-efetivos")
@Tag(name = "Servidores Efetivos", description = "Operações relacionadas a servidores efetivos")
public class ServidorEfetivoController {

    @Autowired
    private ServidorEfetivoService servidorEfetivoService;

    @Operation(summary = "Lista todos os servidores efetivos",
            description = "Retorna uma lista paginada de todos os servidores efetivos.",
            tags = {"Servidores Efetivos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de servidores efetivos retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum servidor efetivo encontrado",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<Page<ServidorEfetivoDTO>> getAllServidoresEfetivos(@Parameter(
            description = "Objeto de paginação para controlar o número de elementos por página, a página atual e a ordenação",
            schema = @Schema(implementation = Pageable.class)) Pageable pageable) {
        Page<ServidorEfetivoDTO> servidores =
                servidorEfetivoService.getAllServidoresEfetivos(pageable);
        return servidores.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(servidores);
    }

    @Operation(summary = "Busca um servidor efetivo por ID",
            description = "Retorna os detalhes de um servidor efetivo específico com base no seu ID.",
            tags = {"Servidores Efetivos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servidor efetivo encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Servidor efetivo não encontrado",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<ServidorEfetivoDTO> getServidorEfetivoById(
            @Parameter(description = "ID do servidor efetivo a ser buscado",
                    required = true) @PathVariable Long id) {
        return ResponseEntity.ok(servidorEfetivoService.getServidorEfetivoById(id));
    }

    @Operation(summary = "Cria um novo servidor efetivo",
            description = "Cria um novo registro de servidor efetivo.",
            tags = {"Servidores Efetivos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Servidor efetivo criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<ServidorEfetivoDTO> createServidorEfetivo(@Parameter(
            description = "Dados do servidor efetivo a serem criados", required = true,
            schema = @Schema(
                    implementation = ServidorEfetivoDTO.class)) @RequestBody ServidorEfetivoDTO servidorEfetivoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servidorEfetivoService.createServidorEfetivo(servidorEfetivoDTO));
    }

    @Operation(summary = "Atualiza um servidor efetivo existente",
            description = "Atualiza os dados de um servidor efetivo específico com base no seu ID.",
            tags = {"Servidores Efetivos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Servidor efetivo atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Servidor efetivo não encontrado",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<ServidorEfetivoDTO> updateServidorEfetivo(
            @Parameter(description = "ID do servidor efetivo a ser atualizado",
                    required = true) @PathVariable Long id,
            @Parameter(description = "Novos dados do servidor efetivo", required = true,
                    schema = @Schema(
                            implementation = ServidorEfetivoDTO.class)) @RequestBody ServidorEfetivoDTO servidorEfetivoDTO) {
        return ResponseEntity
                .ok(servidorEfetivoService.updateServidorEfetivo(id, servidorEfetivoDTO));
    }

    @Operation(summary = "Exclui um servidor efetivo por ID",
            description = "Remove um servidor efetivo específico com base no seu ID.",
            tags = {"Servidores Efetivos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Servidor efetivo excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Servidor efetivo não encontrado",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServidorEfetivo(
            @Parameter(description = "ID do servidor efetivo a ser excluído",
                    required = true) @PathVariable Long id) {
        servidorEfetivoService.deleteServidorEfetivo(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona um endereço a um servidor efetivo existente",
            description = "Adiciona um novo endereço à servidor efetivo especificada.",
            tags = {"Servidores Efetivos", "Endereços"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço adicionado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivo.class))),
            @ApiResponse(responseCode = "404", description = "Servidor Efetivo não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content)})
    @PostMapping("/{id}/enderecos")
    public ResponseEntity<ServidorEfetivoDTO> addEnderecoToServidorEfetivo(
            @Parameter(description = "ID do servidor efetivo para adicionar o endereço",
                    required = true) @PathVariable Long id,
            @Parameter(description = "Dados do endereço a serem adicionados", required = true,
                    schema = @Schema(
                            implementation = EnderecoDTO.class)) @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity
                .ok(servidorEfetivoService.addEnderecoToServidorEfetivo(id, enderecoDTO));
    }

    @Operation(summary = "Lista os servidores efetivos por ID da unidade com paginação",
            description = "Retorna uma página de servidores efetivos pertencentes a uma unidade específica.",
            tags = {"Busca"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Página de servidores efetivos da unidade retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ConsultaServidorLotadoPorUnidadeDTO.class))),
            @ApiResponse(responseCode = "204",
                    description = "Nenhum servidor efetivo encontrado para esta unidade nesta página",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada",
                    content = @Content)})
    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<Page<ConsultaServidorLotadoPorUnidadeDTO>> getServidoresEfetivosByUnidade(
            @Parameter(description = "ID da unidade para buscar os servidores efetivos",
                    required = true) @PathVariable Long unidadeId,
            Pageable pageable) {
        Page<ConsultaServidorLotadoPorUnidadeDTO> consulta =
                servidorEfetivoService.findServidoresEfetivosLotadosPorUnidade(unidadeId, pageable);
        return consulta.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(consulta);
    }

    @Operation(
            summary = "Consulta paginada de endereço funcional de servidores efetivos por nome (parcial ou completo)",
            description = "Retorna uma página de endereços de unidades de servidores efetivos cujo nome corresponde ao parâmetro de busca.",
            tags = {"Busca"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Página de endereços funcionais de servidores efetivos encontrados por nome retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(
                    implementation = ConsultaEnderecoFuncionalServidorEfetivoPorNomeDTO.class))),
            @ApiResponse(responseCode = "204",
                    description = "Nenhum servidor efetivo encontrado com este nome nesta página",
                    content = @Content)})
    @GetMapping("/endereco-funcional")
    public ResponseEntity<Page<ConsultaEnderecoFuncionalServidorEfetivoPorNomeDTO>> getEnderecoFuncionalByServidorNomeContaining(
            @Parameter(description = "Nome (ou parte do nome) do servidor efetivo a ser buscado",
                    required = true) @RequestParam("nome") String nome,
            Pageable pageable) {
        Page<ConsultaEnderecoFuncionalServidorEfetivoPorNomeDTO> servidores =
                servidorEfetivoService.getEnderecoFuncionalByServidorNomeContaining(nome, pageable);
        return servidores.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(servidores);
    }
}
