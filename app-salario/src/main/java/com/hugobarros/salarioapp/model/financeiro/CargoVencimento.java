package com.hugobarros.salarioapp.model.financeiro;

import com.hugobarros.salarioapp.model.pessoal.Cargo;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cargo_vencimentos", schema = "financeiro")
public class CargoVencimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "vencimento_id")
    private Vencimento vencimento;
}
