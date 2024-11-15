package com.sena.crudempleados.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private Long idProducto;

    @Column(name = "producNom", nullable = false, length = 20)
    private String producNom;

    @Column(name = "producPrecio", nullable = false)
    private Float producPrecio;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "tipoPrenda", nullable = false, length = 15)
    private String tipoPrenda;

    // Constructor vacío
    public Producto() {
    }

    // Constructor con todos los atributos, incluyendo idProducto
    public Producto(Long idProducto, String producNom, Float producPrecio, Integer cantidad, String tipoPrenda) {
        this.idProducto = idProducto;
        this.producNom = producNom;
        this.producPrecio = producPrecio;
        this.cantidad = cantidad;
        this.tipoPrenda = tipoPrenda;
    }

    // Constructor sin idProducto, ideal para nuevos productos
    public Producto(String producNom, Float producPrecio, Integer cantidad, String tipoPrenda) {
        this.producNom = producNom;
        this.producPrecio = producPrecio;
        this.cantidad = cantidad;
        this.tipoPrenda = tipoPrenda;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoPrenda() {
        return tipoPrenda;
    }

    public void setTipoPrenda(String tipoPrenda) {
        this.tipoPrenda = tipoPrenda;
    }
}
