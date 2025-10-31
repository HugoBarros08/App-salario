package com.hugobarros.salarioapp.model.seguranca;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario", schema = "seguranca")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    @Column(name = "senha_hash")
    private String senhaHash;

    private String nome;
    private Boolean ativo;
}
