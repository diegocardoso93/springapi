package com.projeto.springapi.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ServidorTemporarioDTO {
    private Long pesId;
    private String pesNome;
    private LocalDate pesDataNascimento;
    private String pesSexo;
    private String pesMae;
    private String pesPai;
    private LocalDate stDataAdmissao;
    private LocalDate stDataDemissao;
}
