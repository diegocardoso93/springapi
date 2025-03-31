package com.projeto.springapi.repository;

import com.projeto.springapi.model.FotoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FotoPessoaRepository extends JpaRepository<FotoPessoa, Long> {
    List<FotoPessoa> findByPessoaPesId(Long pesId);
}
