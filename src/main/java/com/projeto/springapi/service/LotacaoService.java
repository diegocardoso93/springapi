package com.projeto.springapi.service;

import com.projeto.springapi.dto.LotacaoDTO;
import com.projeto.springapi.exception.ResourceNotFoundException;
import com.projeto.springapi.model.Lotacao;
import com.projeto.springapi.model.Pessoa;
import com.projeto.springapi.model.Unidade;
import com.projeto.springapi.repository.LotacaoRepository;
import com.projeto.springapi.repository.PessoaRepository;
import com.projeto.springapi.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LotacaoService {

    @Autowired
    private LotacaoRepository lotacaoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private UnidadeRepository unidadeRepository;

    private LotacaoDTO mapToDTO(Lotacao lotacao) {
        LotacaoDTO dto = new LotacaoDTO();
        dto.setLotId(lotacao.getLotId());
        dto.setPesId(lotacao.getPessoa().getPesId());
        dto.setUnidId(lotacao.getUnidade().getUnidId());
        dto.setLotDataLotacao(lotacao.getLotDataLotacao());
        dto.setLotDataRemocao(lotacao.getLotDataRemocao());
        dto.setLotPortaria(lotacao.getLotPortaria());
        return dto;
    }

    public Page<LotacaoDTO> getAllLotacoes(Pageable pageable) {
        return lotacaoRepository.findAll(pageable).map(this::mapToDTO);
    }

    public LotacaoDTO getLotacaoById(Long id) {
        Lotacao lotacao = lotacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lotação não encontrada com id: " + id));
        return mapToDTO(lotacao);
    }

    public LotacaoDTO createLotacao(LotacaoDTO lotacaoDTO) {
        Lotacao lotacao = new Lotacao();

        Pessoa pessoa = pessoaRepository.findById(lotacaoDTO.getPesId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Pessoa não encontrada com id: " + lotacaoDTO.getPesId()));
        lotacao.setPessoa(pessoa);

        Unidade unidade = unidadeRepository.findById(lotacaoDTO.getUnidId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Unidade não encontrada com id: " + lotacaoDTO.getUnidId()));
        lotacao.setUnidade(unidade);

        lotacao.setLotDataLotacao(lotacaoDTO.getLotDataLotacao());
        lotacao.setLotDataRemocao(lotacaoDTO.getLotDataRemocao());
        lotacao.setLotPortaria(lotacaoDTO.getLotPortaria());
        lotacao = lotacaoRepository.save(lotacao);
        return mapToDTO(lotacao);
    }

    public LotacaoDTO updateLotacao(Long id, LotacaoDTO lotacaoDTO) {
        Lotacao lotacao = lotacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lotação não encontrada com id: " + id));

        Pessoa pessoa = pessoaRepository.findById(lotacaoDTO.getPesId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Pessoa não encontrada com id: " + lotacaoDTO.getPesId()));
        lotacao.setPessoa(pessoa);

        Unidade unidade = unidadeRepository.findById(lotacaoDTO.getUnidId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Unidade não encontrada com id: " + lotacaoDTO.getUnidId()));
        lotacao.setUnidade(unidade);

        lotacao.setLotDataLotacao(lotacaoDTO.getLotDataLotacao());
        lotacao.setLotDataRemocao(lotacaoDTO.getLotDataRemocao());
        lotacao.setLotPortaria(lotacaoDTO.getLotPortaria());
        lotacao = lotacaoRepository.save(lotacao);
        return mapToDTO(lotacao);
    }

    public void deleteLotacao(Long id) {
        lotacaoRepository.deleteById(id);
    }
}
