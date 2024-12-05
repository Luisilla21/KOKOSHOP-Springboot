package com.sena.kokoshop.dto;

import com.sena.kokoshop.entidades.Empleado;
import com.sena.kokoshop.entidades.Usuario;

public class UsuarioEmpleadoDTO {

    private Usuario usuario;
    private Empleado empleado;

    public UsuarioEmpleadoDTO() {
    }

    public UsuarioEmpleadoDTO(Usuario usuario, Empleado empleado) {
        this.usuario = usuario;
        this.empleado = empleado;
    }

    // Getters y Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Long getIdUsuario() {
        return usuario.getUsuarioID();
    }

    public Long getIdEmpleado() {
        return empleado.getId();
    }
}
