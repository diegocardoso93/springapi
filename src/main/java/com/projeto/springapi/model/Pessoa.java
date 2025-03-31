package com.projeto.springapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Where;

import lombok.Data;

@Entity
@Table(name = "pessoa")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pes_id")
    private Long pesId;

    @Column(name = "pes_nome")
    private String pesNome;

    @Column(name = "pes_data_nascimento")
    private LocalDate pesDataNascimento;

    @Column(name = "pes_sexo")
    private String pesSexo;

    @Column(name = "pes_mae")
    private String pesMae;

    @Column(name = "pes_pai")
    private String pesPai;

    @ManyToMany
    @JoinTable(
        name = "pessoa_endereco",
        joinColumns = @JoinColumn(name = "pes_id"),
        inverseJoinColumns = @JoinColumn(name = "end_id")
    )
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "pessoa")
    private List<Lotacao> lotacoes = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id", referencedColumnName = "pessoa_pes_id", insertable = false, updatable = false)
    @Where(clause = "lot_data_remocao IS NULL")
    private Lotacao lotacaoAtual;

    public Lotacao getLotacaoAtual() {
        return getLotacoes().stream()
                .filter(l -> l.getLotDataRemocao() == null)
                .findFirst()
                .orElse(null);
    }

}
