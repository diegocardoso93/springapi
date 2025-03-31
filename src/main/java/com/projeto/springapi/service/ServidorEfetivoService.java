package com.projeto.springapi.service;

import com.projeto.springapi.dto.ServidorEfetivoDTO;
import com.projeto.springapi.exception.ResourceNotFoundException;
import com.projeto.springapi.model.Endereco;
import com.projeto.springapi.model.FotoPessoa;
import com.projeto.springapi.model.ServidorEfetivo;
import com.projeto.springapi.repository.EnderecoRepository;
import com.projeto.springapi.repository.FotoPessoaRepository;
import com.projeto.springapi.repository.ServidorEfetivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

    private ServidorEfetivoDTO mapToDTO(ServidorEfetivo servidorEfetivo) {
        ServidorEfetivoDTO dto = new ServidorEfetivoDTO();
        dto.setPesId(servidorEfetivo.getPesId());
        dto.setPesNome(servidorEfetivo.getPesNome());
        dto.setPesDataNascimento(servidorEfetivo.getPesDataNascimento());
        dto.setPesSexo(servidorEfetivo.getPesSexo());
        dto.setPesMae(servidorEfetivo.getPesMae());
        dto.setPesPai(servidorEfetivo.getPesPai());
        dto.setSeMatricula(servidorEfetivo.getSeMatricula());
        if (servidorEfetivo.getLotacaoAtual() != null) {
            dto.setUnidadeId(servidorEfetivo.getLotacaoAtual().getUnidade().getUnidId());
            dto.setUnidadeNome(servidorEfetivo.getLotacaoAtual().getUnidade().getUnidNome());
        }

        FotoPessoa fotoPessoa = fotoPessoaRepository.findByPessoaPesId(servidorEfetivo.getPesId()).stream().findFirst()
                .orElse(null);
        if (fotoPessoa != null) {
            dto.setFotoLink("/api/fotos/links/" + servidorEfetivo.getPesId());
        }

        return dto;
    }

    public Page<ServidorEfetivoDTO> getAllServidoresEfetivos(Pageable pageable) {
        return servidorEfetivoRepository.findAll(pageable).map(this::mapToDTO);
    }

    public ServidorEfetivoDTO getServidorEfetivoById(Long id) {
        ServidorEfetivo servidorEfetivo = servidorEfetivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servidor Efetivo não encontrado com id: " + id));
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

        List<Endereco> enderecos = enderecoRepository.findAllById(dto.getEnderecoIds());
        if (enderecos.size() != dto.getEnderecoIds().size()) {
            throw new RuntimeException("Um ou mais IDs de endereços inválidos");
        }
        servidorEfetivo.setEnderecos(enderecos);

        servidorEfetivo = servidorEfetivoRepository.save(servidorEfetivo);
        return mapToDTO(servidorEfetivo);
    }

    public ServidorEfetivoDTO updateServidorEfetivo(Long id, ServidorEfetivoDTO dto) {
        ServidorEfetivo servidorEfetivo = servidorEfetivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servidor Efetivo não encontrado com id: " + id));
        servidorEfetivo.setSeMatricula(dto.getSeMatricula());
        servidorEfetivo.setPesNome(dto.getPesNome());
        servidorEfetivo.setPesDataNascimento(dto.getPesDataNascimento());
        servidorEfetivo.setPesSexo(dto.getPesSexo());
        servidorEfetivo.setPesMae(dto.getPesMae());
        servidorEfetivo.setPesPai(dto.getPesPai());

        List<Endereco> enderecos = enderecoRepository.findAllById(dto.getEnderecoIds());
        if (enderecos.size() != dto.getEnderecoIds().size()) {
            throw new RuntimeException("Um ou mais IDs de endereços inválidos");
        }
        servidorEfetivo.setEnderecos(enderecos);

        servidorEfetivo = servidorEfetivoRepository.save(servidorEfetivo);
        return mapToDTO(servidorEfetivo);
    }

    public void deleteServidorEfetivo(Long id) {
        servidorEfetivoRepository.deleteById(id);
    }

    public List<ServidorEfetivoDTO> getServidoresEfetivosByUnidade(Long unidadeId) {
        List<ServidorEfetivo> servidores = servidorEfetivoRepository.findByLotacaoUnidadeUnidId(unidadeId);
        return servidores.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ServidorEfetivoDTO> getServidoresEfetivosByNome(String nome) {
        List<ServidorEfetivo> servidores = servidorEfetivoRepository.findByPessoaPesNomeContaining(nome);
        return servidores.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
