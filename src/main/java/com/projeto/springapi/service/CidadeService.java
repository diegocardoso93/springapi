package com.projeto.springapi.service;

import com.projeto.springapi.dto.CidadeDTO;
import com.projeto.springapi.exception.ResourceNotFoundException;
import com.projeto.springapi.model.Cidade;
import com.projeto.springapi.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    private CidadeDTO mapToDTO(Cidade cidade) {
        CidadeDTO dto = new CidadeDTO();
        dto.setCidId(cidade.getCidId());
        dto.setNome(cidade.getNome());
        dto.setCidUf(cidade.getCidUf());
        return dto;
    }

    public Page<CidadeDTO> getAllCidades(Pageable pageable) {
        return cidadeRepository.findAll(pageable).map(this::mapToDTO);
    }

    public CidadeDTO getCidadeById(Long id) {
        Cidade cidade = cidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada com ID: " + id));
        return mapToDTO(cidade);
    }

    public CidadeDTO createCidade(CidadeDTO dto) {
        Cidade cidade = new Cidade();
        cidade.setNome(dto.getNome());
        cidade.setCidUf(dto.getCidUf());
        Cidade savedCidade = cidadeRepository.save(cidade);
        return mapToDTO(savedCidade);
    }

    public CidadeDTO updateCidade(Long id, CidadeDTO dto) {
        Cidade existingCidade = cidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada com ID: " + id));
        existingCidade.setNome(dto.getNome());
        existingCidade.setCidUf(dto.getCidUf());
        Cidade updatedCidade = cidadeRepository.save(existingCidade);
        return mapToDTO(updatedCidade);
    }

    public void deleteCidade(Long id) {
        if (!cidadeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cidade não encontrada com ID: " + id);
        }
        cidadeRepository.deleteById(id);
    }
}
