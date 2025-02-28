package com.sena.kokoshop.repositorio;

import org.springframework.stereotype.Repository;

import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.entidades.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    Optional<Usuario> findByEmail(String email);
}
