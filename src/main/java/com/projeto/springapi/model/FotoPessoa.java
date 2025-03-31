package com.projeto.springapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Table(name = "foto_pessoa")
@Data
public class FotoPessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fp_id")
    private Long fpId;

    @Column(name = "fp_data")
    private LocalDate fpData;

    @Column(name = "fp_bucket")
    private String fpBucket;

    @Column(name = "fp_hash")
    private String fpHash;

    @OneToOne
    @JoinColumn(name = "pes_id", unique = true)
    private Pessoa pessoa;

}
