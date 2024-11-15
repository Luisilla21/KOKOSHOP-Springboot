package com.sena.crudempleados.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crudempleados.entidades.Producto;

@Repository

public interface ProductoRepositorio extends JpaRepository<Producto, Long>{

}
