package com.projeto.springapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "unidade_endereco")
@Data
@IdClass(UnidadeEnderecoId.class)
public class UnidadeEndereco {

    @Id
    @Column(name = "unid_id")
    private Long unidId;

    @Id
    @Column(name = "end_id")
    private Long endId;

}
