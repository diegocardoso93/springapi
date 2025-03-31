import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoas", description = "Operações relacionadas a pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Operation(
            summary = "Adiciona um endereço a uma pessoa existente",
            description = "Adiciona um novo endereço à lista de endereços de uma pessoa específica.",
            tags = {"Endereços"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço adicionado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pessoa.class))),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content)
    })
    @PostMapping("/{pessoaId}/enderecos")
    public ResponseEntity<Pessoa> addEnderecoToPessoa(
            @Parameter(description = "ID da pessoa para adicionar o endereço", required = true) @PathVariable Integer pessoaId,
            @Parameter(description = "Dados do endereço a serem adicionados", required = true,
                    schema = @Schema(implementation = EnderecoRequest.class)) @RequestBody EnderecoRequest enderecoRequest) {
        Pessoa updatedPessoa = pessoaService.addEnderecoToPessoa(pessoaId, enderecoRequest);
        return ResponseEntity.ok(updatedPessoa);
    }

    @Operation(
            summary = "Atualiza os endereços de uma pessoa existente",
            description = "Substitui a lista de endereços de uma pessoa específica pelos IDs de endereço fornecidos.",
            tags = {"Endereços"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereços atualizados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pessoa.class))),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada ou um dos endereços não existe",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content)
    })
    @PutMapping("/{pessoaId}/enderecos")
    public ResponseEntity<Pessoa> updatePessoaEnderecos(
            @Parameter(description = "ID da pessoa para atualizar os endereços", required = true) @PathVariable Integer pessoaId,
            @Parameter(description = "Lista de IDs dos endereços a serem associados à pessoa", required = true,
                    schema = @Schema(implementation = Integer.class)) @RequestBody List<Integer> enderecoIds) {
        Pessoa updatedPessoa = pessoaService.updatePessoaEnderecos(pessoaId, enderecoIds);
        return ResponseEntity.ok(updatedPessoa);
    }
}
