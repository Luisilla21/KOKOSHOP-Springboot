package com.sena.kokoshop.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.entidades.Rol;
import com.sena.kokoshop.entidades.Usuarios;
import com.sena.kokoshop.repositorio.RolRepositorio;
import com.sena.kokoshop.repositorio.UsuariosRepositorio;

@Service
public class UsuariosServiceImpl implements UsuariosService, UserDetailsService {

    private final UsuariosRepositorio usuariosRepositorio;
    private final RolRepositorio rolRepositorio;
    private final PasswordEncoder passwordEncoder;

    public UsuariosServiceImpl(UsuariosRepositorio usuariosRepositorio, RolRepositorio rolRepositorio, PasswordEncoder passwordEncoder) {
        this.usuariosRepositorio = usuariosRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuarios guardar(Usuarios usuarios) {
        usuarios.setPassword(passwordEncoder.encode(usuarios.getPassword()));
        Rol rol = rolRepositorio.findByNombre("ROLE_USER");
        usuarios.setRoles(Collections.singletonList(rol));
        return usuariosRepositorio.save(usuarios);
    }

    @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("Buscando usuario con email: " + username);
    Usuarios usuarios = usuariosRepositorio.findByEmail(username);
    if (usuarios == null) {
        System.out.println("Usuario no encontrado.");
        throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + username);
    }
    System.out.println("Usuario encontrado: " + usuarios.getEmail());
    return User.builder()
            .username(usuarios.getEmail())
            .password(usuarios.getPassword())
            .roles(usuarios.getRoles().stream().map(Rol::getNombre).toArray(String[]::new))
            .build();
}


}
