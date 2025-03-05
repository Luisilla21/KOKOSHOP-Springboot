package com.sena.kokoshop.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.sena.kokoshop.entidades.ProductoCarrito;

@Repository
public interface ProductoCarritoRepositorio extends JpaRepository<ProductoCarrito, Long> {
    @Query("SELECT pc FROM ProductoCarrito pc WHERE pc.carrito.idCarrito = :idCarrito")
    List<ProductoCarrito> findByCarrito_Id(@Param("idCarrito") Long idCarrito);
    List<ProductoCarrito> findByProductoIdProductoAndCarritoIdCarrito(Long idProducto, Long idCarrito);
}