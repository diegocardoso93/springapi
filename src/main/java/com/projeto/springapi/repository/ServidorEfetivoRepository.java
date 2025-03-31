package com.projeto.springapi.repository;

import com.projeto.springapi.model.ServidorEfetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Long> {
    @Query("SELECT se FROM ServidorEfetivo se JOIN FETCH se.lotacaoAtual l JOIN FETCH l.unidade u WHERE l.unidade.unidId = :unidadeId")
    List<ServidorEfetivo> findByLotacaoUnidadeUnidId(Long unidadeId);

    @Query("SELECT se FROM ServidorEfetivo se JOIN FETCH se.lotacaoAtual l JOIN FETCH l.unidade u WHERE se.pesNome LIKE CONCAT('%', :nome, '%')")
    List<ServidorEfetivo> findByPessoaPesNomeContaining(String nome);
}
