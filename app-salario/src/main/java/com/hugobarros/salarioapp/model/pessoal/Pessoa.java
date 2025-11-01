package com.hugobarros.salarioapp.model.pessoal;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pessoa", schema = "pessoal")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String cidade;
    private String email;
    private String cep;
    private String enderco;
    private String pais;
    private String usuario;
    private String telefone;
    private String data_nascimento;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;
}
