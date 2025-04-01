package com.projeto.springapi.dto;

import lombok.Data;

@Data
public class EnderecoDTO {
    private Long endId;
    private String endTipoLogradouro;
    private String endLogradouro;
    private Integer endNumero;
    private String endBairro;
    private Long cidadeId;
}
