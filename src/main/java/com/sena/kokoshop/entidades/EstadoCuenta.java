package com.sena.kokoshop.entidades;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class EstadoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "estadoCuenta", cascade = CascadeType.ALL)
    private List<Usuario> usuario = new ArrayList<>();

    public EstadoCuenta(){}

    public EstadoCuenta(Long id, String nombre, List<Usuario> usuario) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
    }

    public EstadoCuenta(String nombre, List<Usuario> usuario) {
        this.nombre = nombre;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(List<Usuario> usuario) {
        this.usuario = usuario;
    }    

    

}
