package com.projeto.springapi.repository;

import com.projeto.springapi.model.Lotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotacaoRepository extends JpaRepository<Lotacao, Long> {
}
