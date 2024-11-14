package com.sena.crudempleados.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sena.crudempleados.entidades.Empleado;

@Repository

public interface EmpleadoRepositorio extends JpaRepository<Empleado, Long>  {

}
