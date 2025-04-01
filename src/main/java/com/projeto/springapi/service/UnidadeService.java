package com.projeto.springapi.service;

import com.projeto.springapi.dto.EnderecoDTO;
import com.projeto.springapi.dto.UnidadeDTO;
import com.projeto.springapi.exception.ResourceNotFoundException;
import com.projeto.springapi.model.Endereco;
import com.projeto.springapi.model.Unidade;
import com.projeto.springapi.repository.EnderecoRepository;
import com.projeto.springapi.repository.UnidadeRepository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UnidadeService {

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoService enderecoService;

    private UnidadeDTO mapToDTO(Unidade unidade) {
        UnidadeDTO dto = new UnidadeDTO();
        dto.setUnidId(unidade.getUnidId());
        dto.setUnidNome(unidade.getUnidNome());
        dto.setUnidSigla(unidade.getUnidSigla());
        dto.setEnderecoIds(unidade.getEnderecos().stream().map(Endereco::getEndId)
                .collect(Collectors.toList()));
        return dto;
    }

    public Page<UnidadeDTO> getAllUnidades(Pageable pageable) {
        return unidadeRepository.findAll(pageable).map(this::mapToDTO);
    }

    public UnidadeDTO getUnidadeById(Long id) {
        Unidade unidade = unidadeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Unidade não encontrada com id: " + id));
        return mapToDTO(unidade);
    }

    public UnidadeDTO createUnidade(UnidadeDTO unidadeDTO) {
        Unidade unidade = new Unidade();
        unidade.setUnidNome(unidadeDTO.getUnidNome());
        unidade.setUnidSigla(unidadeDTO.getUnidSigla());

        if (unidadeDTO.getEnderecoIds() != null && unidadeDTO.getEnderecoIds().size() > 0) {
            List<Endereco> enderecos = enderecoRepository.findAllById(unidadeDTO.getEnderecoIds());
            if (enderecos.size() != unidadeDTO.getEnderecoIds().size()) {
                throw new RuntimeException("Um ou mais IDs de endereços inválidos");
            }
            unidade.setEnderecos(enderecos);
        }

        unidade = unidadeRepository.save(unidade);
        return mapToDTO(unidade);
    }

    public UnidadeDTO updateUnidade(Long id, UnidadeDTO unidadeDTO) {
        Unidade unidade = unidadeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Unidade não encontrada com id: " + id));
        unidade.setUnidNome(unidadeDTO.getUnidNome());
        unidade.setUnidSigla(unidadeDTO.getUnidSigla());

        if (unidadeDTO.getEnderecoIds() != null && unidadeDTO.getEnderecoIds().size() > 0) {
            List<Endereco> enderecos = enderecoRepository.findAllById(unidadeDTO.getEnderecoIds());
            if (enderecos.size() != unidadeDTO.getEnderecoIds().size()) {
                throw new RuntimeException("Um ou mais IDs de endereços inválidos");
            }
            unidade.setEnderecos(enderecos);
        }

        unidade = unidadeRepository.save(unidade);
        return mapToDTO(unidade);
    }

    public void deleteUnidade(Long id) {
        unidadeRepository.deleteById(id);
    }

    public UnidadeDTO addEnderecoToUnidade(Long id, EnderecoDTO enderecoDTO) {
        Unidade unidade = unidadeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Unidade não encontrada com id: " + id));

        EnderecoDTO enderecoCriado = enderecoService.createEndereco(enderecoDTO);
        List<Endereco> enderecos = unidade.getEnderecos();
        if (enderecos.size() > 0) {
            Endereco newEndereco = new Endereco();
            newEndereco.setEndId(enderecoCriado.getEndId());
            enderecos.add(newEndereco);
        }
        unidade.setEnderecos(enderecos);

        unidade = unidadeRepository.save(unidade);
        return mapToDTO(unidade);
    }
}
