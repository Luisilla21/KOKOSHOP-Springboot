package com.sena.kokoshop.entidades;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    @Column(name = "producDescripcion", nullable = false, length = 255)
    private String producDescripcion;


    @Column(name = "tipoPrenda", nullable = false, length = 15)
    private String tipoPrenda;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Lob
    @Column(columnDefinition = "LONGBLOB") // Para imágenes grandes
    private byte[] imagen;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<ProductoVenta> productosVenta = new ArrayList<>();

    // Constructor vacío
    public Producto() {
    }

    // Constructor con todos los atributos, incluyendo idProducto
    public Producto(Long idProducto, String producNom, Float producPrecio, String tipoPrenda,
            Integer cantidad, byte[] imagen, List<ProductoVenta> productosVenta) {
        this.idProducto = idProducto;
        this.producNom = producNom;
        this.producPrecio = producPrecio;
        this.tipoPrenda = tipoPrenda;
        this.cantidad = cantidad;
        this.imagen = imagen;
        this.productosVenta = productosVenta;
    }

    // Constructor sin idProducto, ideal para nuevos productos
    public Producto(String producNom, Float producPrecio, String tipoPrenda,
            Integer cantidad, byte[] imagen, List<ProductoVenta> productosVenta) {
        this.producNom = producNom;
        this.producPrecio = producPrecio;
        this.tipoPrenda = tipoPrenda;
        this.cantidad = cantidad;
        this.imagen = imagen;
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

    public String getProducDescripcion() {
        return producDescripcion;
    }

    public void setProducDescripcion(String producDescripcion) {
        this.producDescripcion = producDescripcion;
    }

    public String getTipoPrenda() {
        return tipoPrenda;
    }

    public void setTipoPrenda(String tipoPrenda) {
        this.tipoPrenda = tipoPrenda;
    }

    public List<ProductoVenta> getProductosVenta() {
        return productosVenta;
    }

    public void setProductosVenta(List<ProductoVenta> productosVenta) {
        this.productosVenta = productosVenta;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Producto [idProducto=" + idProducto + ", producNom=" + producNom + ",productDescripcion" + producDescripcion + ", producPrecio=" + producPrecio
                + ", tipoPrenda=" + tipoPrenda + "]";
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

}
