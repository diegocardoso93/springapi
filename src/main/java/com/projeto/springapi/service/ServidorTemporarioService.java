package com.projeto.springapi.service;

import com.projeto.springapi.dto.ServidorTemporarioDTO;
import com.projeto.springapi.exception.ResourceNotFoundException;
import com.projeto.springapi.model.ServidorTemporario;
import com.projeto.springapi.repository.ServidorTemporarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServidorTemporarioService {

    @Autowired
    private ServidorTemporarioRepository servidorTemporarioRepository;

    private ServidorTemporarioDTO mapToDTO(ServidorTemporario servidorTemporario) {
        ServidorTemporarioDTO dto = new ServidorTemporarioDTO();
        dto.setPesId(servidorTemporario.getPesId());
        dto.setPesNome(servidorTemporario.getPesNome());
        dto.setPesDataNascimento(servidorTemporario.getPesDataNascimento());
        dto.setPesSexo(servidorTemporario.getPesSexo());
        dto.setPesMae(servidorTemporario.getPesMae());
        dto.setPesPai(servidorTemporario.getPesPai());
        dto.setStDataAdmissao(servidorTemporario.getStDataAdmissao());
        dto.setStDataDemissao(servidorTemporario.getStDataDemissao());
        return dto;
    }

    public Page<ServidorTemporarioDTO> getAllServidoresTemporarios(Pageable pageable) {
        return servidorTemporarioRepository.findAll(pageable).map(this::mapToDTO);
    }

    public ServidorTemporarioDTO getServidorTemporarioById(Long id) {
        ServidorTemporario servidorTemporario = servidorTemporarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Servidor Temporário não encontrado com id: " + id));
        return mapToDTO(servidorTemporario);
    }

    public ServidorTemporarioDTO createServidorTemporario(
            ServidorTemporarioDTO servidorTemporarioDTO) {
        ServidorTemporario servidorTemporario = new ServidorTemporario();
        servidorTemporario.setPesId(servidorTemporarioDTO.getPesId());
        servidorTemporario.setPesNome(servidorTemporarioDTO.getPesNome());
        servidorTemporario.setPesDataNascimento(servidorTemporarioDTO.getPesDataNascimento());
        servidorTemporario.setPesSexo(servidorTemporarioDTO.getPesSexo());
        servidorTemporario.setPesMae(servidorTemporarioDTO.getPesMae());
        servidorTemporario.setPesPai(servidorTemporarioDTO.getPesPai());
        servidorTemporario.setStDataAdmissao(servidorTemporarioDTO.getStDataAdmissao());
        servidorTemporario.setStDataDemissao(servidorTemporarioDTO.getStDataDemissao());
        servidorTemporario = servidorTemporarioRepository.save(servidorTemporario);
        return mapToDTO(servidorTemporario);
    }

    public ServidorTemporarioDTO updateServidorTemporario(Long id,
            ServidorTemporarioDTO servidorTemporarioDTO) {
        ServidorTemporario servidorTemporario = servidorTemporarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Servidor Temporário não encontrado com id: " + id));
        servidorTemporario.setStDataAdmissao(servidorTemporarioDTO.getStDataAdmissao());
        servidorTemporario.setStDataDemissao(servidorTemporarioDTO.getStDataDemissao());
        servidorTemporario = servidorTemporarioRepository.save(servidorTemporario);
        return mapToDTO(servidorTemporario);
    }

    public void deleteServidorTemporario(Long id) {
        servidorTemporarioRepository.deleteById(id);
    }
}
