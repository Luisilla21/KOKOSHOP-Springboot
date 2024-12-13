package com.sena.kokoshop.entidades;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "cantidadTalla")
public class CantidadTalla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "talla", length = 5, nullable = false)
    private String talla;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    @OneToMany(mappedBy = "cantidadTalla", cascade = CascadeType.ALL)
    private List<ProductoVenta> productosVenta = new ArrayList<>();

    public CantidadTalla() {

    }

    public CantidadTalla(Long id, Integer cantidad, Producto producto, String talla, List<ProductoVenta> productosVenta) {
        this.id = id;
        this.cantidad = cantidad;
        this.producto = producto;
        this.talla = talla;
        this.productosVenta = productosVenta;
    }

    public CantidadTalla(Integer cantidad, Producto producto, String talla, List<ProductoVenta> productosVenta) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.talla = talla;
        this.productosVenta = productosVenta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<ProductoVenta> getProductosVenta() {
        return productosVenta;
    }

    public void setProductosVenta(List<ProductoVenta> productosVenta) {
        this.productosVenta = productosVenta;
    }



}
