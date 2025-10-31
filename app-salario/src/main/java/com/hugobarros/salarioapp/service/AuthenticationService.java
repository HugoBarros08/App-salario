package com.hugobarros.salarioapp.service;

import com.hugobarros.salarioapp.model.seguranca.Usuario;
import com.hugobarros.salarioapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario autenticar(String login, String senha) {
        String hash = gerarHash(senha);
        return usuarioRepository.findByLogin(login)
                .filter(u -> u.getSenhaHash().equals(hash) && Boolean.TRUE.equals(u.getAtivo()))
                .orElse(null);
    }

    public String gerarHash(String senha) {
        return DigestUtils.md5DigestAsHex(senha.getBytes(StandardCharsets.UTF_8));
    }
}
