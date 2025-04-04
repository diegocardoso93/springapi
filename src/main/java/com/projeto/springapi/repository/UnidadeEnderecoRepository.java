package com.projeto.springapi.repository;

import com.projeto.springapi.model.UnidadeEndereco;
import com.projeto.springapi.model.UnidadeEnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeEnderecoRepository
        extends JpaRepository<UnidadeEndereco, UnidadeEnderecoId> {
}
