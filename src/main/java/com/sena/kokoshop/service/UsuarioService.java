package com.sena.kokoshop.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        }

        // Verificar si la cuenta está deshabilitada
        if (!usuario.isEnabled()) {
            throw new DisabledException("La cuenta está deshabilitada.");
        }

        return usuario;
    }
}
