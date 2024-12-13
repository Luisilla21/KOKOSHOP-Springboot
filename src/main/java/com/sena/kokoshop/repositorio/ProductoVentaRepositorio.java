package com.sena.kokoshop.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sena.kokoshop.entidades.ProductoVenta;

@Repository
public interface ProductoVentaRepositorio extends JpaRepository<ProductoVenta, Long>{
    @Query("SELECT ct FROM ProductoVenta ct WHERE ct.venta.idVenta = :idVenta")
    List<ProductoVenta> obtenerProductosPorIdVenta(@Param("idVenta") Long idVenta);
}


