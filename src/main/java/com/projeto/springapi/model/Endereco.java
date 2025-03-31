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
    private String endTipoLogradouro;

    @Column(name = "end_logradouro")
    private String endLogradouro;

    @Column(name = "end_numero")
    private Integer endNumero;

    @Column(name = "end_bairro")
    private String endBairro;

    @ManyToOne
    @JoinColumn(name = "cid_id")
    private Cidade cidade;

}
