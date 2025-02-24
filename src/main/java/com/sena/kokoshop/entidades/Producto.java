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
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(name = "producNom", nullable = false, length = 20)
    private String producNom;

    @Column(name = "producPrecio", nullable = false)
    private Float producPrecio;

    @Column(name = "tipoPrenda", nullable = false, length = 15)
    private String tipoPrenda;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<CantidadTalla> tallas = new ArrayList<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<ProductoVenta> productosVenta = new ArrayList<>();
   
    @ManyToOne
@JoinColumn(name = "venta_id")
private Venta venta;


    // Constructor vacío
    public Producto() {
    }

    // Constructor con todos los atributos, incluyendo idProducto
    public Producto(Long idProducto, String producNom, Float producPrecio, String tipoPrenda,
            List<CantidadTalla> tallas, List<ProductoVenta> productosVenta) {
        this.idProducto = idProducto;
        this.producNom = producNom;
        this.producPrecio = producPrecio;
        this.tipoPrenda = tipoPrenda;
        this.tallas = tallas;
        this.productosVenta = productosVenta;
    }

    // Constructor sin idProducto, ideal para nuevos productos
    public Producto(String producNom, Float producPrecio, String tipoPrenda,
            List<CantidadTalla> tallas, List<ProductoVenta> productosVenta) {
        this.producNom = producNom;
        this.producPrecio = producPrecio;
        this.tipoPrenda = tipoPrenda;
        this.tallas = tallas;
        this.productosVenta = productosVenta;
    }

    // Getters y Setters

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getProducNom() {
        return producNom;
    }

    public void setProducNom(String producNom) {
        this.producNom = producNom;
    }

    public Float getProducPrecio() {
        return producPrecio;
    }

    public void setProducPrecio(Float producPrecio) {
        this.producPrecio = producPrecio;
    }

    public String getTipoPrenda() {
        return tipoPrenda;
    }

    public void setTipoPrenda(String tipoPrenda) {
        this.tipoPrenda = tipoPrenda;
    }

    public List<CantidadTalla> getTallas() {
        return tallas;
    }

    public void setTallas(List<CantidadTalla> tallas) {
        this.tallas = tallas;
    }

    public List<ProductoVenta> getProductosVenta() {
        return productosVenta;
    }

    public void setProductosVenta(List<ProductoVenta> productosVenta) {
        this.productosVenta = productosVenta;
    }

    @Override
    public String toString() {
        return "Producto [idProducto=" + idProducto + ", producNom=" + producNom + ", producPrecio=" + producPrecio
                + ", tipoPrenda=" + tipoPrenda + "]";
    }

}
