package com.projeto.springapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Table(name = "lotacao")
@Data
public class Lotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id")
    private Long lotId;

    @Column(name = "lot_data_lotacao")
    private LocalDate lotDataLotacao;

    @Column(name = "lot_data_remocao")
    private LocalDate lotDataRemocao;

    @Column(name = "lot_portaria")
    private String lotPortaria;

    @ManyToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "unid_id")
    private Unidade unidade;

}
