package com.sena.kokoshop.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sena.kokoshop.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email); 

    @Query("SELECT u FROM Usuario u WHERE u.rol.nombre = 'CLIENTE'")
    List<Usuario> findUsuariosByRolCliente();
}
