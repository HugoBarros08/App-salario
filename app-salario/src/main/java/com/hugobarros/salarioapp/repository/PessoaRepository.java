package com.hugobarros.salarioapp.repository;

import com.hugobarros.salarioapp.model.pessoal.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
}
