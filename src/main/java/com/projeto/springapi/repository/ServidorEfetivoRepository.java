package com.projeto.springapi.repository;

import com.projeto.springapi.model.ServidorEfetivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Long> {

    @Query(value = "SELECT p.pes_id as pesId, p.pes_nome as nome, "
            + "(EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM p.pes_data_nascimento)) AS idade, "
            + "u.unid_nome as unidade "
            + "FROM servidor_efetivo se JOIN pessoa p ON se.pes_id = p.pes_id "
            + "JOIN lotacao l ON p.pes_id = l.pes_id " + "JOIN unidade u ON l.unid_id = u.unid_id "
            + "WHERE u.unid_id = :unidId " + "AND l.lot_data_remocao IS NULL",
            countQuery = "SELECT COUNT(p.pes_id) FROM servidor_efetivo se "
                    + "JOIN lotacao l ON p.pes_id = l.pes_id "
                    + "JOIN unidade u ON l.unid_id = u.unid_id " + "WHERE u.unid_id = :unidId "
                    + "AND l.lot_data_remocao IS NULL",
            nativeQuery = true)
    Page<Object[]> findServidoresEfetivosLotadosPorUnidade(@Param("unidId") Long unidId,
            Pageable pageable);

    @Query(value = "SELECT p.pes_id, p.pes_nome, u.unid_id, u.unid_nome, u.unid_sigla, "
            + "e.end_id, e.end_tipo_logradouro, e.end_logradouro, e.end_numero, e.end_bairro, c.cid_id, c.cid_nome, c.cid_uf "
            + "FROM servidor_efetivo se JOIN pessoa p ON se.pes_id = p.pes_id "
            + "JOIN lotacao l ON se.pes_id = l.pes_id JOIN unidade u ON l.unid_id = u.unid_id "
            + "JOIN unidade_endereco ue ON ue.unid_id = u.unid_id JOIN endereco e ON ue.end_id = e.end_id "
            + "JOIN cidade c ON e.cid_id = c.cid_id "
            + "WHERE p.pes_nome LIKE CONCAT('%', :nome, '%') " + "AND l.lot_data_remocao IS NULL",
            countQuery = "SELECT COUNT(se.pes_id) FROM servidor_efetivo se JOIN pessoa p ON se.pes_id = p.pes_id "
                    + "JOIN lotacao l ON se.pes_id = l.pes_id JOIN unidade u ON l.unid_id = u.unid_id "
                    + "JOIN unidade_endereco ue ON ue.unid_id = u.unid_id JOIN endereco e ON ue.end_id = e.end_id "
                    + "JOIN cidade c ON e.cid_id = c.cid_id "
                    + "WHERE p.pes_nome LIKE CONCAT('%', :nome, '%') "
                    + "AND l.lot_data_remocao IS NULL",
            nativeQuery = true)
    Page<Object[]> findEnderecoFuncionalByServidorNomeContaining(@Param("nome") String nome,
            Pageable pageable);

}
