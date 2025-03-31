package com.projeto.springapi.service;

import com.projeto.springapi.dto.EnderecoDTO;
import com.projeto.springapi.exception.ResourceNotFoundException;
import com.projeto.springapi.model.Cidade;
import com.projeto.springapi.model.Endereco;
import com.projeto.springapi.repository.CidadeRepository;
import com.projeto.springapi.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    private EnderecoDTO mapToDTO(Endereco endereco) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setEndId(endereco.getEndId());
        dto.setEndTipoLogradouro(endereco.getEndTipoLogradouro());
        dto.setEndLogradouro(endereco.getEndLogradouro());
        dto.setEndNumero(endereco.getEndNumero());
        dto.setEndBairro(endereco.getEndBairro());
        if (endereco.getCidade() != null) {
            dto.setCidadeId(endereco.getCidade().getCidId());
            dto.setCidadeNome(endereco.getCidade().getCidNome());
            dto.setUfSigla(endereco.getCidade().getUf().getUfSigla());
        }
        return dto;
    }

    public Page<EnderecoDTO> getAllEnderecos(Pageable pageable) {
        return enderecoRepository.findAll(pageable).map(this::mapToDTO);
    }

    public EnderecoDTO getEnderecoById(Integer id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + id));
        return mapToDTO(endereco);
    }

    public EnderecoDTO createEndereco(EnderecoDTO dto) {
        Cidade cidade = cidadeRepository.findById(dto.getCidadeId())
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada com ID: " + dto.getCidadeId()));

        Endereco endereco = new Endereco();
        endereco.setEndTipoLogradouro(dto.getEndTipoLogradouro());
        endereco.setEndLogradouro(dto.getEndLogradouro());
        endereco.setEndNumero(dto.getEndNumero());
        endereco.setEndBairro(dto.getEndBairro());
        endereco.setCidade(cidade);

        Endereco savedEndereco = enderecoRepository.save(endereco);
        return mapToDTO(savedEndereco);
    }

    public EnderecoDTO updateEndereco(Integer id, EnderecoDTO dto) {
        Endereco existingEndereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + id));

        Cidade cidade = cidadeRepository.findById(dto.getCidadeId())
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada com ID: " + dto.getCidadeId()));

        existingEndereco.setEndTipoLogradouro(dto.getEndTipoLogradouro());
        existingEndereco.setEndLogradouro(dto.getEndLogradouro());
        existingEndereco.setEndNumero(dto.getEndNumero());
        existingEndereco.setEndBairro(dto.getEndBairro());
        existingEndereco.setCidade(cidade);

        Endereco updatedEndereco = enderecoRepository.save(existingEndereco);
        return mapToDTO(updatedEndereco);
    }

    public void deleteEndereco(Integer id) {
        if (!enderecoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Endereço não encontrado com ID: " + id);
        }
        enderecoRepository.deleteById(id);
    }
}
