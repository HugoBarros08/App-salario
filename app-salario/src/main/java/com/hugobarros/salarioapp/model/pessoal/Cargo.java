package com.hugobarros.salarioapp.model.pessoal;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cargo", schema = "pessoal")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
}
