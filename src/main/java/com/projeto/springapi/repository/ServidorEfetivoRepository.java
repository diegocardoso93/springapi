package com.projeto.springapi.repository;

import com.projeto.springapi.model.ServidorEfetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Long> {
    // @Query("SELECT se FROM ServidorEfetivo se JOIN FETCH se.pessoa p JOIN FETCH
    // se.lotacao l JOIN FETCH l.unidade u WHERE l.unidade.unidId = :unidadeId")
    // List<ServidorEfetivo> findByLotacaoUnidadeUnidId(Long unidadeId);

    // @Query("SELECT se FROM ServidorEfetivo se JOIN FETCH se.pessoa p JOIN FETCH
    // se.lotacao l JOIN FETCH l.unidade u WHERE p.pesNome LIKE %:nome%")
    // List<ServidorEfetivo> findByPessoaPesNomeContaining(String nome);
}
