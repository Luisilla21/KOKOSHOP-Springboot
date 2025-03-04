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
@Table(name = "carritos")
public class Carrito {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    @ManyToOne
    @JoinColumn(name = "idCli", nullable = false, unique = true)
    private Usuario cliente;

    @Column(nullable = false)
    private Float precioTotal;

    
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<ProductoCarrito> productos = new ArrayList<>();

    public Carrito() {
    }

    public Carrito(Long idCarrito, Usuario cliente, Float precioTotal, List<ProductoCarrito> productos) {
        this.idCarrito = idCarrito;
        this.cliente = cliente;
        this.precioTotal = precioTotal;
        this.productos = productos;
    }

    public Carrito(Usuario cliente, Float precioTotal, List<ProductoCarrito> productos) {
        this.cliente = cliente;
        this.precioTotal = precioTotal;
        this.productos = productos;
    }

    public Long getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Long idVenta) {
        this.idCarrito = idVenta;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }


    public Float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public List<ProductoCarrito> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCarrito> productos) {
        this.productos = productos;
    }

    

}
