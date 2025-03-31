package com.projeto.springapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "endereco")
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id")
    private Long endId;

    @Column(name = "end_tipo_logradouro")
    private String tipoLogradouro;

    @Column(name = "end_logradouro")
    private String logradouro;

    @Column(name = "end_numero")
    private Integer numero;

    @Column(name = "end_bairro")
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "cid_id")
    private Cidade cidade;
}
