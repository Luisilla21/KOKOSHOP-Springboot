package com.sena.kokoshop.entidades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class Usuario implements UserDetails {

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

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 150)
    private String password;

    @Column(name = "reset_token", unique = true, length = 100)
    private String resetToken;

    @Column(name = "telefono", nullable = true, length = 10)
    private String telefono;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Empleado empleado;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Venta> compras = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "idEstado", nullable = false)
    private EstadoCuenta estadoCuenta;

    // Constructor sin parámetros
    public Usuario() {
    }

    // Constructor con todos los parámetros
    public Usuario(Long id, String nombre, String apellido, String numeroDocumento, String tipoDocumento,
            String direccion, String ciudad,
            String email, String password, String telefono,
            Empleado empleado, List<Venta> compras, Rol rol, EstadoCuenta estadoCuenta) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.empleado = empleado;
        this.compras = compras;
        this.rol = rol;
        this.estadoCuenta = estadoCuenta;
    }

    // Constructor sin el ID
    public Usuario(String nombre, String apellido, String numeroDocumento, String tipoDocumento, String direccion,
            String ciudad,
            String email, String password, String telefono,
            Empleado empleado, List<Venta> compras, Rol rol, EstadoCuenta estadoCuenta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.empleado = empleado;
        this.compras = compras;
        this.rol = rol;
        this.estadoCuenta = estadoCuenta;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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

    public List<Venta> getCompras() {
        return compras;
    }

    public void setCompras(List<Venta> compras) {
        this.compras = compras;
    }

    public EstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devuelve los roles/autoridades del usuario
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol.getNombre()));
    }

    @Override
    public String getUsername() {
        return email; // El email es el nombre de usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // La cuenta no expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // La cuenta no está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Las credenciales no expiran
    }

    @Override
    public boolean isEnabled() {
        return estadoCuenta != null && "HABILITADA".equalsIgnoreCase(estadoCuenta.getNombre());
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
        sb.append(", correoElectronico=").append(email);
        sb.append(", telefono=").append(telefono);
        sb.append(", empleado=").append(empleado.getSalario());
        sb.append('}');
        return sb.toString();
    }
}
