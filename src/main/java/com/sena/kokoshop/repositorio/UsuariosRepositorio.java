package com.sena.kokoshop.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.kokoshop.entidades.Usuarios;

public interface UsuariosRepositorio extends JpaRepository<Usuarios, Long> {
    Usuarios findByEmail(String email);
}
