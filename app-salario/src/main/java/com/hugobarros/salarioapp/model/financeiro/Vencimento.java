package com.hugobarros.salarioapp.model.financeiro;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vencimentos", schema = "financeiro")
public class Vencimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;
    private Integer valor;

    @Enumerated(EnumType.STRING)
    private TipoVencimento tipo;
}
