package com.projeto.springapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "servidor_temporario")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "pes_id")
public class ServidorTemporario extends Pessoa {

    @Id
    @Column(name = "pes_id")
    private Long pesId;

    @Column(name = "st_data_admissao")
    private LocalDate stDataAdmissao;

    @Column(name = "st_data_demissao")
    private LocalDate stDataDemissao;

}
