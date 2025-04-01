package com.projeto.springapi.repository;

import com.projeto.springapi.model.ServidorEfetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Long> {

    @Query("SELECT p.pesId as pesId, p.pesNome as nome, "
            + "(YEAR(CURRENT_DATE) - YEAR(p.pesDataNascimento)) AS idade, "
            + "u.unidNome as unidade " + "FROM ServidorEfetivo p " + "JOIN p.lotacoes l "
            + "JOIN l.unidade u " + "WHERE u.unidId = :unidId " + "AND l.lotDataRemocao IS NULL")
    List<Object[]> findServidoresEfetivosLotadosPorUnidade(@Param("unidId") Long unidId);

    @Query("SELECT se.pesId, se.pesNome, u.unidId, u.unidNome, u.unidSigla, "
            + "e.endId, e.endTipoLogradouro, e.endLogradouro, e.endNumero, e.endBairro, c.cidId, c.cidNome, c.cidUf "
            + "FROM ServidorEfetivo se JOIN se.lotacoes l "
            + "JOIN l.unidade u JOIN u.enderecos e JOIN e.cidade c "
            + "WHERE se.pesNome LIKE CONCAT('%', :nome, '%') " + "AND l.lotDataRemocao IS NULL")
    List<Object[]> findEnderecoFuncionalByServidorNomeContaining(String nome);

}
