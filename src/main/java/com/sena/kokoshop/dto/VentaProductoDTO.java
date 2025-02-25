package com.sena.kokoshop.dto;

import java.util.ArrayList;
import java.util.List;

import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.entidades.ProductoVenta;

public class VentaProductoDTO {
    private Venta venta;
    private List<ProductoVenta> productosVenta = new ArrayList<>();

    public VentaProductoDTO(){

    }

    public VentaProductoDTO(Venta venta, List<ProductoVenta> productosVenta) {
        this.venta = venta;
        this.productosVenta = productosVenta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<ProductoVenta> getProductosVenta() {
        return productosVenta;
    }

    public void setProductos(List<ProductoVenta> productosVenta) {
        this.productosVenta = productosVenta;
    }

    public Long getVentaId(){
        return venta.getIdVenta();
    }

    
}
