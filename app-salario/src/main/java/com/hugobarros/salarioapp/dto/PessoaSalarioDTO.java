package com.hugobarros.salarioapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaSalarioDTO {
    private Integer pessoaId;
    private String nomePessoa;
    private String nomeCargo;
    private Double salario;
}
