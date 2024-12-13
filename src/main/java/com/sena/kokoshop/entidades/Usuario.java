package com.sena.kokoshop.entidades;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = true, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = true, length = 100)
    private String apellido;

    @Column(name = "tipoDocumento", nullable = true, length = 30)
    private String tipoDocumento;

    @Column(name = "numeroDocumento", nullable = true, unique = true, length = 10)
    private String numeroDocumento;

    @Column(name = "direccion", nullable = true, length = 255)
    private String direccion;

    @Column(name = "ciudad", nullable = true, length = 100)
    private String ciudad;

    @Column(name = "correoElectronico", nullable = false, unique = true, length = 150)
    private String correoElectronico;

    @Column(name = "telefono", nullable = true, length = 10)
    private String telefono;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Empleado empleado;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Ventas> compras = new ArrayList<>();

    // Constructor sin parámetros
    public Usuario() {
    }

    // Constructor con todos los parámetros
    public Usuario(Long id, String nombre, String apellido, String numeroDocumento, String tipoDocumento,
            String direccion, String ciudad,
            String correoElectronico, String telefono, String historialCompras,
            Empleado empleado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.empleado = empleado;
    }

    // Constructor sin el ID
    public Usuario(String nombre, String apellido, String numeroDocumento, String tipoDocumento, String direccion,
            String ciudad,
            String correoElectronico, String telefono, String historialCompras,
            Empleado empleado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.empleado = empleado;
    }

    // Getters y Setters
    public long getUsuarioID() {
        return id;
    }

    public void setUsuarioID(Long id) {
        this.id = id;
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

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Ventas> getCompras() {
        return compras;
    }

    public void setCompras(List<Ventas> compras) {
        this.compras = compras;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario{");
        sb.append("id=").append(id);
        sb.append(", nombre=").append(nombre);
        sb.append(", apellido=").append(apellido);
        sb.append(", direccion=").append(direccion);
        sb.append(", ciudad=").append(ciudad);
        sb.append(", correoElectronico=").append(correoElectronico);
        sb.append(", telefono=").append(telefono);
        sb.append(", empleado=").append(empleado.getSalario());
        sb.append('}');
        return sb.toString();
    }



}
