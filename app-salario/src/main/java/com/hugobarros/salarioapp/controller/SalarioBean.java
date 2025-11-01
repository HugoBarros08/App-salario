package com.hugobarros.salarioapp.controller;

import com.hugobarros.salarioapp.dto.PessoaSalarioDTO;
import com.hugobarros.salarioapp.service.SalarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;

import java.io.Serializable;
import java.util.List;

@Component("salarioBean")
@ViewScoped
public class SalarioBean implements Serializable {

    @Autowired
    private SalarioService salarioService;

    private List<PessoaSalarioDTO> lista;

    @PostConstruct
    public void init() {
        lista = salarioService.listarConsolidado();
    }

    public void recalcular() {
        salarioService.recalcularSalarios();
        lista = salarioService.listarConsolidado();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Recalculado com sucesso", null));
    }

    public List<PessoaSalarioDTO> getLista() {
        return lista;
    }
}
