package com.projeto.springapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class UnidadeDTO {
    private Long unidId;
    private String unidNome;
    private String unidSigla;

    private List<Long> enderecoIds;
}
