package com.sena.kokoshop.interfaz;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.entidades.Rol;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;

@Service
public class UsuarioImplementacion implements UsuarioInterfaz {
    @Autowired
    private UsuarioRepositorio repositorio;

    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return repositorio.findAll();
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return repositorio.save(usuario);
    }

    @Override
    public Usuario obtenerUsuarioporId(long id) {
        return repositorio.findById(id).get();
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        return repositorio.save(usuario);
    }

    @Override
    public void eliminarUsuario(long id) {
        repositorio.deleteById(id);
    }

    @Override
    public List<Usuario> obtenerUsuariosPorRolCliente() {
        return repositorio.findUsuariosByRolCliente();
    }

    @Override
public Usuario findByEmail(String email) {
    return repositorio.findByEmail(email);  // Devuelve un Usuario sin Optional
}

@Override
public Optional<Usuario> findByResetToken(String token) {
    return repositorio.findByResetToken(token);  // Maneja Optional para evitar errores
}

}