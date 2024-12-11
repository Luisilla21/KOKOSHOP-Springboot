package com.sena.kokoshop.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.kokoshop.entidades.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}
