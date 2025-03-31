package com.projeto.springapi.repository;

import com.projeto.springapi.model.PessoaEndereco;
import com.projeto.springapi.model.PessoaEnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaEnderecoRepository extends JpaRepository<PessoaEndereco, PessoaEnderecoId> {
}
