package com.projeto.springapi.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ServidorEfetivoDTO {
    private Long pesId;
    private String pesNome;
    private LocalDate pesDataNascimento;
    private String pesSexo;
    private String pesMae;
    private String pesPai;
    private String seMatricula;
    private Long unidadeId;
    private String unidadeNome;
    private String fotoLink;
}
