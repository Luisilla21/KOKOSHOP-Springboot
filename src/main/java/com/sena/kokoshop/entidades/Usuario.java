package com.sena.kokoshop.entidades;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioID;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "ciudad", length = 100)
    private String ciudad;

    @Column(name = "estado", length = 100)
    private String estado;

    @Column(name = "correoElectronico", nullable = false, unique = true, length = 150)
    private String correoElectronico;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "fechaRegistro",updatable = false)
    @CreationTimestamp
    private Timestamp fechaRegistro;

    @Column(name = "historialCompras", columnDefinition = "TEXT")
    private String historialCompras;

    // Constructor sin parámetros
    public Usuario() {
    }

    // Constructor con todos los parámetros
    public Usuario(Long usuarioID, String nombre, String apellido, String direccion, String ciudad, String estado,
                   String correoElectronico, String telefono, Timestamp fechaRegistro, String historialCompras) {
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estado = estado;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.historialCompras = historialCompras;
    }

    // Constructor sin el ID
    public Usuario(String nombre, String apellido, String direccion, String ciudad, String estado,
                   String correoElectronico, String telefono, Timestamp fechaRegistro, String historialCompras) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estado = estado;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.historialCompras = historialCompras;
    }

    // Getters y Setters

    public Long getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Long usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getHistorialCompras() {
        return historialCompras;
    }

    public void setHistorialCompras(String historialCompras) {
        this.historialCompras = historialCompras;
    }
}
