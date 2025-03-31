package com.projeto.springapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "servidor_efetivo")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "pes_id")
public class ServidorEfetivo extends Pessoa {

    @Id
    @Column(name = "pes_id")
    private Long pesId;

    @Column(name = "se_matricula")
    private String seMatricula;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade lotacaoUnidade;

}
