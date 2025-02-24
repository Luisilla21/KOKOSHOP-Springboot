package com.sena.kokoshop.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.kokoshop.entidades.Venta;

@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Long> {
}
