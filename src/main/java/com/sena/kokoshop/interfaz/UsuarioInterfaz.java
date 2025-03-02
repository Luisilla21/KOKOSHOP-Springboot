package com.sena.kokoshop.interfaz;

import java.util.List;

import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.entidades.Rol;

public interface UsuarioInterfaz {

    public List<Usuario> listarTodosLosUsuarios();

    public Usuario guardarUsuario(Usuario usuario);

    public Usuario obtenerUsuarioporId(long id);

    public Usuario actualizarUsuario(Usuario Usuario);

    public void eliminarUsuario(long id);

    public List<Usuario> obtenerUsuariosPorRolCliente();
}
