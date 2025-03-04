package com.sena.kokoshop.dto;

import java.util.ArrayList;
import java.util.List;

import com.sena.kokoshop.entidades.Carrito;
import com.sena.kokoshop.entidades.ProductoCarrito;

public class CarritoProductoDTO {
    private Carrito carrito;
    private List<ProductoCarrito> productosCarrito = new ArrayList<>();

    public CarritoProductoDTO() {

    }

    public CarritoProductoDTO(Carrito carrito, List<ProductoCarrito> productosCarrito) {
        this.carrito = carrito;
        this.productosCarrito = productosCarrito;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public List<ProductoCarrito> getProductosCarrito() {
        return productosCarrito;
    }

    public void setProductosCarrito(List<ProductoCarrito> productosCarrito) {
        this.productosCarrito = productosCarrito;
    }

    public Long getCarritoId() {
        return carrito.getIdCarrito();
    }
    
}
