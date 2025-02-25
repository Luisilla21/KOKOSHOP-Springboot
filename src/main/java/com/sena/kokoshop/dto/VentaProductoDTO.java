package com.sena.kokoshop.dto;

import java.util.ArrayList;
import java.util.List;

import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.entidades.ProductoVenta;

public class VentaProductoDTO {
    private Venta venta;
    private List<ProductoVenta> productos = new ArrayList<>();

    public VentaProductoDTO(){

    }

    public VentaProductoDTO(Venta venta, List<ProductoVenta> productos) {
        this.venta = venta;
        this.productos = productos;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<ProductoVenta> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoVenta> productos) {
        this.productos = productos;
    }

    
}
