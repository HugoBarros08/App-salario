package com.hugobarros.salarioapp.controller;

import com.hugobarros.salarioapp.dto.PessoaSalarioDTO;
import com.hugobarros.salarioapp.service.SalarioService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import jakarta.inject.Named;
import java.util.List;

@Named("salarioBean")
@ViewScoped
public class SalarioBean implements Serializable {

    @Inject
    private SalarioService salarioService;
    private List<PessoaSalarioDTO> lista;
    private boolean processamentoConcluido = false;

    @PostConstruct
    public void init() {
        lista = salarioService.listarConsolidado();
    }

    public void recalcular() {
        processamentoConcluido = false;
        salarioService.recalcularSalariosAsync(() -> processamentoConcluido = true);
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cálculo iniciado em segundo plano", null));
    }

    public void verificarConclusao() {
        if (processamentoConcluido) {
            lista = salarioService.listarConsolidado();
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cálculo concluído e lista atualizada", null));
            processamentoConcluido = false;
        }
    }

    public List<PessoaSalarioDTO> getLista() {
        return lista;
    }
}
