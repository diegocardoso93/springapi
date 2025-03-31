package com.projeto.springapi.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class LotacaoDTO {
    private Long lotId;
    private Long pesId;
    private Long unidId;
    private LocalDate lotDataLotacao;
    private LocalDate lotDataRemocao;
    private String lotPortaria;
}
