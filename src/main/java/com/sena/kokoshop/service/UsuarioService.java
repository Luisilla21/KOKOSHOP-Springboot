package com.sena.kokoshop.service;

import com.sena.kokoshop.entidades.Usuario;

public interface UsuarioService {
    Usuario guardar(Usuario usuario);
    
    /**
     * Verifica si ya existe un usuario con el email especificado
     * @param email El email a verificar
     * @return true si el email ya existe, false en caso contrario
     */
    boolean existeEmail(String email);
}