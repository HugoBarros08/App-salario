package com.hugobarros.salarioapp.repository;

import com.hugobarros.salarioapp.model.seguranca.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
}