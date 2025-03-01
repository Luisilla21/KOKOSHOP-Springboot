package com.sena.kokoshop.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.entidades.Rol;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.repositorio.RolRepositorio;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final RolRepositorio rolRepositorio;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepositorio usuarioRepositorio, RolRepositorio rolRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Rol rol = rolRepositorio.findByNombre("ROLE_USER");
        usuario.setRoles(Collections.singletonList(rol));
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public boolean existeEmail(String email) {
        return usuarioRepositorio.findByEmail(email) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Buscando usuario con email: " + username);
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + username);
        }
        System.out.println("Usuario encontrado: " + usuario.getEmail());
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRoles().stream().map(Rol::getNombre).toArray(String[]::new))
                .build();
    }
}