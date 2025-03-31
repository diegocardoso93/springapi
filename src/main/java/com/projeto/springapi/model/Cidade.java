package com.projeto.springapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cidade")
@Data
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid_id")
    private Long cidId;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cid_uf")
    private String cidUf;
}
