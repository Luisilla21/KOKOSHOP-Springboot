package com.sena.kokoshop.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.kokoshop.entidades.CantidadTalla;

public interface CantidadTallaRepositorio extends JpaRepository<CantidadTalla, Object> {
    @Query("SELECT ct FROM CantidadTalla ct WHERE ct.producto.idProducto = :idProducto")
    List<CantidadTalla> obtenerTallasPorIdProducto(@Param("idProducto") Long idProducto);
}
