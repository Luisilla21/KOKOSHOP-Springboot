package com.sena.kokoshop.service;

import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioSecurityService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar el usuario por su email (o nombre de usuario)
        Usuario usuario = usuarioRepositorio.findByEmail(email); 



        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        }

        System.out.println("Rol: " + usuario.getRol().getNombre());
        // Convertir el usuario en un UserDetails (usando User de Spring Security)
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getAuthorities() // Aquí debes devolver los roles/autoridades del usuario
        );
    }
}