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

    // Constructor vacío
    public Producto() {
    }

    // Constructor con todos los atributos, incluyendo idProducto
    public Producto(Long idProducto, String producNom, Float producPrecio, String tipoPrenda,
            List<CantidadTalla> tallas) {
        this.idProducto = idProducto;
        this.producNom = producNom;
        this.producPrecio = producPrecio;
        this.tipoPrenda = tipoPrenda;
        this.tallas = tallas;
    }

    // Constructor sin idProducto, ideal para nuevos productos
    public Producto(String producNom, Float producPrecio, String tipoPrenda,
            List<CantidadTalla> tallas) {
        this.producNom = producNom;
        this.producPrecio = producPrecio;
        this.tipoPrenda = tipoPrenda;
        this.tallas = tallas;
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

    @Override
    public String toString() {
        return "Producto [idProducto=" + idProducto + ", producNom=" + producNom + ", producPrecio=" + producPrecio
                + ", tipoPrenda=" + tipoPrenda + "]";
    }

}
