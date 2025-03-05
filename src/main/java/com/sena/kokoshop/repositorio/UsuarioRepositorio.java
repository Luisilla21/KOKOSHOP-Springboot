package com.sena.kokoshop.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sena.kokoshop.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.rol.nombre = 'CLIENTE'")
    List<Usuario> findUsuariosByRolCliente();

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario findByEmail(@Param("email") String email);

    // Devuelve directamente un Usuario
    Optional<Usuario> findByResetToken(String token); // Maneja el caso de que el token no exista

}
