package com.projeto.springapi.service;

import com.projeto.springapi.dto.ConsultaEnderecoFuncionalServidorEfetivoPorNomeDTO;
import com.projeto.springapi.dto.ConsultaServidorLotadoPorUnidadeDTO;
import com.projeto.springapi.dto.EnderecoDTO;
import com.projeto.springapi.dto.ServidorEfetivoDTO;
import com.projeto.springapi.dto.UnidadeDTO;
import com.projeto.springapi.exception.ResourceNotFoundException;
import com.projeto.springapi.model.Endereco;
import com.projeto.springapi.model.FotoPessoa;
import com.projeto.springapi.model.ServidorEfetivo;
import com.projeto.springapi.model.Unidade;
import com.projeto.springapi.repository.EnderecoRepository;
import com.projeto.springapi.repository.FotoPessoaRepository;
import com.projeto.springapi.repository.ServidorEfetivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServidorEfetivoService {

    @Autowired
    private ServidorEfetivoRepository servidorEfetivoRepository;

    @Autowired
    private FotoPessoaRepository fotoPessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private MinIOService minIOService;

    private ServidorEfetivoDTO mapToDTO(ServidorEfetivo servidorEfetivo) {
        ServidorEfetivoDTO dto = new ServidorEfetivoDTO();
        dto.setPesId(servidorEfetivo.getPesId());
        dto.setPesNome(servidorEfetivo.getPesNome());
        dto.setPesDataNascimento(servidorEfetivo.getPesDataNascimento());
        dto.setPesSexo(servidorEfetivo.getPesSexo());
        dto.setPesMae(servidorEfetivo.getPesMae());
        dto.setPesPai(servidorEfetivo.getPesPai());
        dto.setSeMatricula(servidorEfetivo.getSeMatricula());
        dto.setEnderecoIds(servidorEfetivo.getEnderecos().stream().map(Endereco::getEndId)
                .collect(Collectors.toList()));

        // if (servidorEfetivo.getLotacaoAtual() != null) {
        // dto.setUnidadeId(servidorEfetivo.getLotacaoAtual().getUnidade().getUnidId());
        // dto.setUnidadeNome(servidorEfetivo.getLotacaoAtual().getUnidade().getUnidNome());
        // }

        // FotoPessoa fotoPessoa =
        // fotoPessoaRepository.findByPessoaPesId(servidorEfetivo.getPesId())
        // .stream().findFirst().orElse(null);
        // if (fotoPessoa != null) {
        // dto.setFotoLink("/api/fotos/links/" + servidorEfetivo.getPesId());
        // }

        return dto;
    }

    public Page<ServidorEfetivoDTO> getAllServidoresEfetivos(Pageable pageable) {
        return servidorEfetivoRepository.findAll(pageable).map(this::mapToDTO);
    }

    public ServidorEfetivoDTO getServidorEfetivoById(Long id) {
        ServidorEfetivo servidorEfetivo = servidorEfetivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Servidor Efetivo não encontrado com id: " + id));
        return mapToDTO(servidorEfetivo);
    }

    public ServidorEfetivoDTO createServidorEfetivo(ServidorEfetivoDTO dto) {
        ServidorEfetivo servidorEfetivo = new ServidorEfetivo();
        servidorEfetivo.setPesId(dto.getPesId());
        servidorEfetivo.setSeMatricula(dto.getSeMatricula());
        servidorEfetivo.setPesNome(dto.getPesNome());
        servidorEfetivo.setPesDataNascimento(dto.getPesDataNascimento());
        servidorEfetivo.setPesSexo(dto.getPesSexo());
        servidorEfetivo.setPesMae(dto.getPesMae());
        servidorEfetivo.setPesPai(dto.getPesPai());

        if (dto.getEnderecoIds() != null && dto.getEnderecoIds().size() > 0) {
            List<Endereco> enderecos = enderecoRepository.findAllById(dto.getEnderecoIds());
            if (enderecos.size() != dto.getEnderecoIds().size()) {
                throw new RuntimeException("Um ou mais IDs de endereços inválidos");
            }
            servidorEfetivo.setEnderecos(enderecos);
        }

        servidorEfetivo = servidorEfetivoRepository.save(servidorEfetivo);
        return mapToDTO(servidorEfetivo);
    }

    public ServidorEfetivoDTO updateServidorEfetivo(Long id, ServidorEfetivoDTO dto) {
        ServidorEfetivo servidorEfetivo = servidorEfetivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Servidor Efetivo não encontrado com id: " + id));
        servidorEfetivo.setSeMatricula(dto.getSeMatricula());
        servidorEfetivo.setPesNome(dto.getPesNome());
        servidorEfetivo.setPesDataNascimento(dto.getPesDataNascimento());
        servidorEfetivo.setPesSexo(dto.getPesSexo());
        servidorEfetivo.setPesMae(dto.getPesMae());
        servidorEfetivo.setPesPai(dto.getPesPai());

        if (dto.getEnderecoIds() != null && dto.getEnderecoIds().size() > 0) {
            List<Endereco> enderecos = enderecoRepository.findAllById(dto.getEnderecoIds());
            if (enderecos.size() != dto.getEnderecoIds().size()) {
                throw new RuntimeException("Um ou mais IDs de endereços inválidos");
            }
            servidorEfetivo.setEnderecos(enderecos);
        }

        servidorEfetivo = servidorEfetivoRepository.save(servidorEfetivo);
        return mapToDTO(servidorEfetivo);
    }

    public void deleteServidorEfetivo(Long id) {
        servidorEfetivoRepository.deleteById(id);
    }

    public ServidorEfetivoDTO addEnderecoToServidorEfetivo(Long id, EnderecoDTO enderecoDTO) {
        ServidorEfetivo servidorEfetivo = servidorEfetivoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Unidade não encontrada com id: " + id));

        EnderecoDTO enderecoCriado = enderecoService.createEndereco(enderecoDTO);
        List<Endereco> enderecos = servidorEfetivo.getEnderecos();
        if (enderecos.size() > 0) {
            Endereco newEndereco = new Endereco();
            newEndereco.setEndId(enderecoCriado.getEndId());
            enderecos.add(newEndereco);
        }
        servidorEfetivo.setEnderecos(enderecos);

        servidorEfetivo = servidorEfetivoRepository.save(servidorEfetivo);
        return mapToDTO(servidorEfetivo);
    }

    public List<ConsultaServidorLotadoPorUnidadeDTO> findServidoresEfetivosLotadosPorUnidade(
            Long unidadeId) {
        return mapFindServidoresEfetivosLotadosPorUnidadeToDTO(
                servidorEfetivoRepository.findServidoresEfetivosLotadosPorUnidade(unidadeId));
    }

    public List<ConsultaServidorLotadoPorUnidadeDTO> mapFindServidoresEfetivosLotadosPorUnidadeToDTO(
            List<Object[]> result) {
        List<ConsultaServidorLotadoPorUnidadeDTO> dtos = new ArrayList<>();
        for (Object[] row : result) {
            ConsultaServidorLotadoPorUnidadeDTO dto = new ConsultaServidorLotadoPorUnidadeDTO();
            dto.setPesId((Long) row[0]);
            dto.setNome((String) row[1]);
            dto.setIdade((Integer) row[2]);
            dto.setUnidade((String) row[3]);
            List<String> fotoLinks = minIOService.getFotoLinks(dto.getPesId());
            dto.setFotografia(fotoLinks.isEmpty() ? null : fotoLinks.get(0));
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ConsultaEnderecoFuncionalServidorEfetivoPorNomeDTO> getEnderecoFuncionalByServidorNomeContaining(
            String nome) {
        return mapFindEnderecoFuncionalByServidorNomeContainingToDTO(
                servidorEfetivoRepository.findEnderecoFuncionalByServidorNomeContaining(nome));
    }

    public List<ConsultaEnderecoFuncionalServidorEfetivoPorNomeDTO> mapFindEnderecoFuncionalByServidorNomeContainingToDTO(
            List<Object[]> result) {
        List<ConsultaEnderecoFuncionalServidorEfetivoPorNomeDTO> dtos = new ArrayList<>();
        for (Object[] row : result) {
            ConsultaEnderecoFuncionalServidorEfetivoPorNomeDTO dto =
                    new ConsultaEnderecoFuncionalServidorEfetivoPorNomeDTO();
            dto.setPesId((Long) row[0]);
            dto.setPesNome((String) row[1]);
            dto.setUnidId((Long) row[2]);
            dto.setUnidNome((String) row[3]);
            dto.setUnidSigla((String) row[4]);
            dto.setEndId((Long) row[5]);
            dto.setEndTipoLogradouro((String) row[6]);
            dto.setEndLogradouro((String) row[7]);
            dto.setEndNumero((Integer) row[8]);
            dto.setEndBairro((String) row[9]);
            dto.setCidId((Long) row[10]);
            dto.setCidNome((String) row[11]);
            dto.setCidUf((String) row[12]);
            dtos.add(dto);
        }
        return dtos;
    }
}
