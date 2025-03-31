import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    public Endereco createEndereco(EnderecoDTO dto) {
        Cidade cidade = cidadeRepo.findById(dto.getCidadeId())
            .orElseThrow(() -> new RuntimeException("Cidade not found"));

        Endereco endereco = new Endereco();
        Cidade cidade = new Cidade();
        cidade.setCidId(dto.getCidadeId());
        endereco.setTipoLogradouro(dto.getTipoLogradouro());
        endereco.setlogradouro(dto.getlogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(cidade);

        return enderecoRepository.save(endereco);
    }

    public Endereco getEnderecoById(Integer id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Endereco not found with ID: " + id));
    }

    // Other Endereco related methods (e.g., update, delete)
}
