package com.hugobarros.salarioapp.model.financeiro;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pessoa_salario_consolidado", schema = "financeiro")
public class PessoaSalarioConsolidado {

    @Id
    @Column(name = "pessoa_id")
    private Integer pessoaId;

    @Column(name = "nome_pessoa")
    private String nomePessoa;

    @Column(name = "nome_cargo")
    private String nomeCargo;

    private Double salario;
}
