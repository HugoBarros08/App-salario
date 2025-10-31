package com.hugobarros.salarioapp.repository;

import com.hugobarros.salarioapp.model.financeiro.PessoaSalarioConsolidado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaSalarioConsolidadoRepository extends JpaRepository<PessoaSalarioConsolidado, Integer> {
}
