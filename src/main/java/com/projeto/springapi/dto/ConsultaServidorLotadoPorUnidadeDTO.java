package com.projeto.springapi.dto;

import lombok.Data;

@Data
public class ConsultaServidorLotadoPorUnidadeDTO {
    private Long pesId;
    private String nome;
    private Integer idade;
    private String unidade;
    private String linkFoto;
}
