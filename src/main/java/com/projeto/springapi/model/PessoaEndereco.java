package com.projeto.springapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pessoa_endereco")
@Data
@IdClass(PessoaEnderecoId.class) // Indica a classe de chave primária composta
public class PessoaEndereco {

    @Id
    @Column(name = "pes_id")
    private Long pesId;

    @Id
    @Column(name = "end_id")
    private Long endId;

}
