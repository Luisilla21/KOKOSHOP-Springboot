package com.sena.kokoshop.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.kokoshop.entidades.Empleado;

@Repository

public interface EmpleadoRepositorio extends JpaRepository<Empleado, Long>  {

}
 