package com.sena.kokoshop.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @OneToOne
    @JoinColumn(name = "idCli", nullable = false)
    private Usuario cliente;

    @OneToOne
    @JoinColumn(name = "idEmp", nullable = false)
    private Empleado empleado;

    @Column(nullable = false)
    private Float precioTatal;

    @Column(nullable = false)
    private Date fechaVenta;

    @Column(nullable = false, length = 20)
    private String tipoVenta;

    @Column(nullable = false, length = 10)
    private String estadoVenta;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<ProductoVenta> productos = new ArrayList<>();

    public Venta(){
    }

    public Venta(Long idVenta, Usuario cliente, Empleado empleado, Float precioTatal, Date fechaVenta, String tipoVenta, String estadoVenta, List<ProductoVenta> productos) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.empleado = empleado;
        this.precioTatal = precioTatal;
        this.fechaVenta = fechaVenta;
        this.tipoVenta = tipoVenta;
        this.estadoVenta = estadoVenta;
        this.productos = productos;

    }

    public Venta(Usuario cliente, Empleado empleado, Float precioTatal, Date fechaVenta, String tipoVenta, String estadoVenta, List<ProductoVenta> productos) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.precioTatal = precioTatal;
        this.fechaVenta = fechaVenta;
        this.tipoVenta = tipoVenta;
        this.estadoVenta = estadoVenta;
        this.productos = productos;
    }

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Float getPrecioTatal() {
        return precioTatal;
    }

    public void setPrecioTatal(Float precioTatal) {
        this.precioTatal = precioTatal;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(String estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public List<ProductoVenta> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoVenta> productos) {
        this.productos = productos;
    }




}
