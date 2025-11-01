package com.hugobarros.salarioapp.controller;

import com.hugobarros.salarioapp.model.seguranca.Usuario;
import com.hugobarros.salarioapp.service.AuthenticationService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

@Component("loginBean")
@ViewScoped
@Getter
@Setter
public class LoginBean implements Serializable {

    private String usuario;
    private String senha;

    @Autowired
    private AuthenticationService authenticationService;

    public void autenticar() throws IOException {
        Usuario usuarioAutenticado = authenticationService.autenticar(usuario, senha);
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (usuarioAutenticado != null) {
            ctx.getExternalContext().getSessionMap().put("usuarioLogado", usuarioAutenticado);
            ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/pages/salario.xhtml");
        } else {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login ou senha inválidos", null));
        }
    }

    public void verificarSessao() throws IOException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Usuario usuarioLogado = (Usuario) ctx.getExternalContext().getSessionMap().get("usuarioLogado");

        if (usuarioLogado != null) {
            ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/pages/salario.xhtml");
        }
    }

    public void logout() throws IOException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().invalidateSession();
        ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/login.xhtml");
    }

    public String getNomeUsuarioLogado() {
        Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuarioLogado");

        return usuarioLogado != null ? usuarioLogado.getNome() : "";
    }
}
