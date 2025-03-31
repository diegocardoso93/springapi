package com.projeto.springapi.controller;

import com.projeto.springapi.dto.EnderecoDTO;
import com.projeto.springapi.model.Endereco;
import com.projeto.springapi.request.EnderecoRequest;
import com.projeto.springapi.service.EnderecoService;
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
@RequestMapping("/enderecos")
@Tag(name = "Endereços", description = "Operações relacionadas a endereços")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    @Operation(
            summary = "Cria um novo endereço (utilizando EnderecoRequest)",
            description = "Cria um novo registro de endereço com base nos dados fornecidos no corpo da requisição.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do endereço a serem criados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EnderecoRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Endereco.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos")
            }
    )
    public ResponseEntity<Endereco> createEndereco(@RequestBody EnderecoRequest enderecoRequest) {
        Endereco createdEndereco = enderecoService.createEndereco(enderecoRequest);
        return new ResponseEntity<>(createdEndereco, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca um endereço por ID",
            description = "Retorna os detalhes de um endereço específico com base no ID fornecido.",
            parameters = {
                    @Parameter(name = "id", description = "ID do endereço a ser buscado", required = true, schema = @Schema(type = "integer"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Endereço encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Endereco.class))),
                    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
            }
    )
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable Integer id) {
        Endereco endereco = enderecoService.getEnderecoById(id);
        return ResponseEntity.ok(endereco);
    }

    @PostMapping
    @Operation(
            summary = "Cria um novo endereço (utilizando EnderecoDTO)",
            description = "Cria um novo registro de endereço com base nos dados fornecidos no corpo da requisição (DTO).",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do endereço a serem criados (DTO)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EnderecoDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnderecoDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos")
            }
    )
    public ResponseEntity<EnderecoDTO> createEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.createEndereco(enderecoDTO));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um endereço existente",
            description = "Atualiza os dados de um endereço existente com base no ID fornecido e nos dados do corpo da requisição.",
            parameters = {
                    @Parameter(name = "id", description = "ID do endereço a ser atualizado", required = true, schema = @Schema(type = "integer"))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados para o endereço",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EnderecoDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnderecoDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos"),
                    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
            }
    )
    public ResponseEntity<EnderecoDTO> updateEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(enderecoService.updateEndereco(id, enderecoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Exclui um endereço por ID",
            description = "Exclui um registro de endereço com base no ID fornecido.",
            parameters = {
                    @Parameter(name = "id", description = "ID do endereço a ser excluído", required = true, schema = @Schema(type = "integer"))
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Endereço excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
            }
    )
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        enderecoService.deleteEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
