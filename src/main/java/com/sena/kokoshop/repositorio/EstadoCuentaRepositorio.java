package com.sena.kokoshop.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.kokoshop.entidades.EstadoCuenta;

public interface EstadoCuentaRepositorio extends JpaRepository<EstadoCuenta, Long>{
    EstadoCuenta findByNombre(String nombre);
}
