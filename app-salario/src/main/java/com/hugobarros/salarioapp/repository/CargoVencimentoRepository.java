package com.hugobarros.salarioapp.repository;

import com.hugobarros.salarioapp.model.financeiro.CargoVencimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoVencimentoRepository extends JpaRepository<CargoVencimento, Integer> {
}
