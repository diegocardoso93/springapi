package com.projeto.springapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "unidade")
@Data
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unid_id")
    private Long unidId;

    @Column(name = "unid_nome")
    private String unidNome;

    @Column(name = "unid_sigla")
    private String unidSigla;

    @ManyToMany
    @JoinTable(
        name = "unidade_endereco",
        joinColumns = @JoinColumn(name = "unid_id"),
        inverseJoinColumns = @JoinColumn(name = "end_id")
    )
    private List<Endereco> enderecos = new ArrayList<>();

}
