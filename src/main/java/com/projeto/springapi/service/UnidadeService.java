package com.projeto.springapi.service;

import com.projeto.springapi.dto.UnidadeDTO;
import com.projeto.springapi.exception.ResourceNotFoundException;
import com.projeto.springapi.model.Unidade;
import com.projeto.springapi.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UnidadeService {

    @Autowired
    private UnidadeRepository unidadeRepository;

    private UnidadeDTO mapToDTO(Unidade unidade) {
        UnidadeDTO dto = new UnidadeDTO();
        dto.setUnidId(unidade.getUnidId());
        dto.setUnidNome(unidade.getUnidNome());
        dto.setUnidSigla(unidade.getUnidSigla());
        return dto;
    }

    public Page<UnidadeDTO> getAllUnidades(Pageable pageable) {
        return unidadeRepository.findAll(pageable).map(this::mapToDTO);
    }

    public UnidadeDTO getUnidadeById(Long id) {
        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade não encontrada com id: " + id));
        return mapToDTO(unidade);
    }

    public UnidadeDTO createUnidade(UnidadeDTO unidadeDTO) {
        Unidade unidade = new Unidade();
        unidade.setUnidNome(unidadeDTO.getUnidNome());
        unidade.setUnidSigla(unidadeDTO.getUnidSigla());
        unidade = unidadeRepository.save(unidade);
        return mapToDTO(unidade);
    }

    public UnidadeDTO updateUnidade(Long id, UnidadeDTO unidadeDTO) {
        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade não encontrada com id: " + id));
        unidade.setUnidNome(unidadeDTO.getUnidNome());
        unidade.setUnidSigla(unidadeDTO.getUnidSigla());
        unidade = unidadeRepository.save(unidade);
        return mapToDTO(unidade);
    }

    public void deleteUnidade(Long id) {
        unidadeRepository.deleteById(id);
    }
}
