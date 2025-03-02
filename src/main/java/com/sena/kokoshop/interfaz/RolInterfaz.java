package com.sena.kokoshop.interfaz;

import com.sena.kokoshop.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolInterfaz extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}
