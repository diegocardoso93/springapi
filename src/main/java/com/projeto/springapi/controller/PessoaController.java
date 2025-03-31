import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/{pessoaId}/enderecos")
    public ResponseEntity<Pessoa> addEnderecoToPessoa(@PathVariable Integer pessoaId, @RequestBody EnderecoRequest enderecoRequest) {
        Pessoa updatedPessoa = pessoaService.addEnderecoToPessoa(pessoaId, enderecoRequest);
        return ResponseEntity.ok(updatedPessoa);
    }

    @PutMapping("/{pessoaId}/enderecos")
    public ResponseEntity<Pessoa> updatePessoaEnderecos(@PathVariable Integer pessoaId, @RequestBody List<Integer> enderecoIds) {
        Pessoa updatedPessoa = pessoaService.updatePessoaEnderecos(pessoaId, enderecoIds);
        return ResponseEntity.ok(updatedPessoa);
    }
}
