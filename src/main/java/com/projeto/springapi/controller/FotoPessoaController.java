package com.projeto.springapi.controller;

import com.projeto.springapi.dto.LinkFotoDTO;
import com.projeto.springapi.service.MinIOService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fotos")
@Tag(name = "Fotos", description = "Operações relacionadas ao gerenciamento de fotos")
public class FotoPessoaController {

    @Autowired
    private MinIOService minIOService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload de múltiplas fotos",
            description = "Faz o upload de uma ou mais fotos para um determinado ID de pessoa.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Arquivos de imagem a serem enviados e o ID da pessoa associada.",
                    required = true),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Upload realizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = LinkFotoDTO.class)))),
                    @ApiResponse(responseCode = "400",
                            description = "Requisição inválida (por exemplo, arquivos vazios ou ID inválido)"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")})
    public ResponseEntity<List<LinkFotoDTO>> uploadFotos(
            @Parameter(description = "Arquivos de imagem para upload",
                    required = true) @RequestParam("files") MultipartFile[] files,
            @Parameter(description = "ID da pessoa associada às fotos",
                    required = true) @RequestParam("pesId") Long pesId) {
        List<String> links = minIOService.uploadFiles(files, pesId);
        List<LinkFotoDTO> linkFotoDTOs =
                links.stream().map(link -> new LinkFotoDTO(link)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(linkFotoDTOs);
    }

    @GetMapping("/links/{pesId}")
    @Operation(summary = "Lista os links das fotos de uma pessoa",
            description = "Retorna uma lista de links de acesso às fotos associadas a um determinado ID de pessoa.",
            parameters = {@Parameter(name = "pesId",
                    description = "ID da pessoa para buscar os links das fotos", required = true,
                    schema = @Schema(type = "integer", format = "int64"))},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Links das fotos encontrados",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = LinkFotoDTO.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "Nenhuma foto encontrada para o ID da pessoa informado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")})
    public ResponseEntity<List<LinkFotoDTO>> getFotoLinks(
            @Parameter(description = "ID da pessoa", required = true) @PathVariable Long pesId) {
        List<String> links = minIOService.getFotoLinks(pesId);
        List<LinkFotoDTO> linkFotoDTOs =
                links.stream().map(link -> new LinkFotoDTO(link)).collect(Collectors.toList());
        return ResponseEntity.ok(linkFotoDTOs);
    }
}
