package com.sena.kokoshop.entidades;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @Column(nullable = false, length = 100)
    private String cliente;

    @Column(nullable = false, length = 100)
    private String empleado;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Float precioTotal;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
private Date fechaVenta;


    @Column(nullable = false, length = 100)
    private String productoVendido;

    public Venta() {
    }

    public Venta(Long idVenta, String cliente, String empleado, Integer cantidad, Float precioTotal, Date fechaVenta, String productoVendido) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.empleado = empleado;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.fechaVenta = fechaVenta;
        this.productoVendido = productoVendido;
    }

    public Venta(String cliente, String empleado, Integer cantidad, Float precioTotal, Date fechaVenta, String productoVendido) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.fechaVenta = fechaVenta;
        this.productoVendido = productoVendido;
    }

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getProductoVendido() {
        return productoVendido;
    }

    public void setProductoVendido(String productoVendido) {
        this.productoVendido = productoVendido;
    }
}
