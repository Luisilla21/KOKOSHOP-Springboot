package com.sena.kokoshop.interfaz;

import java.util.List;
import java.util.Optional;

import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.entidades.Rol;

public interface UsuarioInterfaz {

    Usuario findByEmail(String email);
      // Sin Optional para evitar errores en otras partes del código
    Optional<Usuario> findByResetToken(String token); // Mantiene Optional para evitar valores nulos


    public List<Usuario> listarTodosLosUsuarios();

    public Usuario guardarUsuario(Usuario usuario);

    public Usuario obtenerUsuarioporId(long id);

    public Usuario actualizarUsuario(Usuario Usuario);

    public void eliminarUsuario(long id);

    public List<Usuario> obtenerUsuariosPorRolCliente();


}
