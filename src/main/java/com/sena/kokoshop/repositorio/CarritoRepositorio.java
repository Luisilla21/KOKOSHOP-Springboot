package com.sena.kokoshop.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.sena.kokoshop.entidades.Carrito;

@Repository
public interface CarritoRepositorio extends JpaRepository<Carrito, Long> {
    Carrito findByCliente_Id(Long idCli);

}
