package com.sena.kokoshop.dto;

import java.util.ArrayList;
import java.util.List;

import com.sena.kokoshop.entidades.CantidadTalla;
import com.sena.kokoshop.entidades.Producto;

public class ProductoTallaDTO {
    private Producto producto;
    private List<CantidadTalla> tallas = new ArrayList<>();

    public ProductoTallaDTO() {

    }

    public ProductoTallaDTO(Producto producto, List<CantidadTalla> tallas) {
        this.producto = producto;
        this.tallas = tallas;
    }

    public int getCantidadTotal() {
        return tallas.stream()
                .mapToInt(CantidadTalla::getCantidad)
                .sum();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<CantidadTalla> getTallas() {
        return tallas;
    }

    public void setTallas(List<CantidadTalla> tallas) {
        this.tallas = tallas;
    }

    public Long getProductoId(){
        return producto.getIdProducto();
    }
}
