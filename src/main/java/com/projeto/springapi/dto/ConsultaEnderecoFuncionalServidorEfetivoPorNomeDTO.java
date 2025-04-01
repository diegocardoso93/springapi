package com.projeto.springapi.dto;

import lombok.Data;

@Data
public class ConsultaEnderecoFuncionalServidorEfetivoPorNomeDTO {
    private Long pesId;
    private String pesNome;

    private Long unidId;
    private String unidSigla;
    private String unidNome;

    private Long endId;
    private String endTipoLogradouro;
    private String endLogradouro;
    private Integer endNumero;
    private String endBairro;

    private Long cidId;
    private String cidNome;
    private String cidUf;
}
